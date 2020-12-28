package transforms

/**
 * Virtual camera, controls view transformation via observer position, azimuth
 * and zenith (in radians). Eye position (the origin of camera coordinate set)
 * can be at the observer position (1st person camera mode) or can orbit the
 * observer position at the given radius (3rd person camera mode). Objects of
 * the class are immutable.
 *
 * @author PGRF FIM UHK
 * @version 2016
 */
class Camera(

    /**
     * Returns the observer position, can be different from the eye (camera)
     * position (depends on 1st/3rd person camera mode)
     *
     * @return eye observer position
     */
    val position: Vec3D = Vec3D(0.0, 0.0, 0.0),

    /**
     * Returns azimuth in radians
     *
     * @return azimuth
     */
    val azimuth: Double = 0.0,

    /**
     * Returns zenith in radians
     *
     * @return zenith
     */
    val zenith: Double = 0.0,
    /**
     * Returns radius (the distance between the eye (camera) and the observer in
     * 3rd person camera mode)
     *
     * @return radius
     */
    val radius: Double = 1.0,
    /**
     * Returns the value of 1st/3rd person camera mode flag
     *
     * @return true -> 1st person mode, false -> 3rd person mode
     */
    val firstPerson // true -> 1st person, false -> 3rd person
    : Boolean = true
) {
    /**
     * Returns the eye (camera) position, depends on the value of the 1st/3rd
     * person camera mode flag
     *
     * @return eye position
     */
    var eye: Vec3D

    /**
     * Returns the view direction as specified by azimuth and zenith
     *
     * @return view vector
     */
    val viewVector: Vec3D = Vec3D(
        (kotlin.math.cos(azimuth) * kotlin.math.cos(zenith)),
        (kotlin.math.sin(azimuth) * kotlin.math.cos(zenith)),
        kotlin.math.sin(zenith)
    )

    /**
     * Returns the view matrix that transforms coordinates from the world
     * coordinate set to the camera coordinate set
     *
     * @return view matrix
     */
    var viewMatrix: Mat4

    /**
     * Creates a camera as a copy of another but with the given 1st/3rd person
     * mode flag
     *
     * @param cam
     * camera to be copied
     * @param firstPerson
     * boolean flag indicating 1st (true) / 3rd (false) person camera
     * mode
     */
    constructor(cam: Camera, firstPerson: Boolean) : this(
        cam.position,
        cam.azimuth,
        cam.zenith,
        cam.radius,
        firstPerson
    ) {
    }

    /**
     * Creates a camera as a copy of another but with the given radius
     *
     * @param cam
     * camera to be copied
     * @param radius
     * distance between the eye (camera origin) and the observer
     * position in the 3rd person camera mode
     */
    constructor(cam: Camera, radius: Double) : this(cam.position, cam.azimuth, cam.zenith, radius, cam.firstPerson) {}

    /**
     * Creates a camera as a copy of another but with the given azimuth and
     * zenith
     *
     * @param cam
     * camera to be copied
     * @param azimuth
     * angle (in radians) between the xz and uv planes where v is the
     * view vector and u is the up vector, i.e. the vector considered
     * vertical by observer, i.e. the vector of the y-axis of the
     * camera coordinate set
     * @param zenith
     * angle (in radians) between the view vector and z-axis
     */
    constructor(cam: Camera, azimuth: Double, zenith: Double) : this(
        cam.position,
        azimuth,
        zenith,
        cam.radius,
        cam.firstPerson
    ) {
    }

    /**
     * Create a camera as a copy of another but with the given observer
     * position
     *
     * @param cam
     * camera to be copied
     * @param pos
     * observer position
     */
    constructor(cam: Camera, pos: Vec3D) : this(pos, cam.azimuth, cam.zenith, cam.radius, cam.firstPerson) {}

    /**
     * Returns a new camera with azimuth summed with the given value
     *
     * @param ang
     * azimuth change in radians
     * @return new Camera instance
     */
    fun addAzimuth(ang: Double): Camera {
        return Camera(this, azimuth + ang, zenith)
    }

    /**
     * Returns a new camera with radius summed with the given value. Radius is
     * kept >= 0.1
     *
     * @param dist
     * radius change amount
     * @return new Camera instance
     */
    fun addRadius(dist: Double): Camera {
        return Camera(this, kotlin.math.max(radius + dist, 0.1))
    }

    /**
     * Returns a new camera with zenith summed with the given value. Zenith is
     * kept in [-pi/2, pi/2]
     *
     * @param ang
     * zenith change in radians
     * @return new Camera instance
     */
    fun addZenith(ang: Double): Camera {
        return Camera(this, azimuth, kotlin.math.max(-kotlin.math.PI / 2, kotlin.math.min(zenith + ang, kotlin.math.PI / 2)))
    }

    /**
     * Returns a new camera moved in the opposite direction of the view vector
     * by the given distance
     *
     * @param speed
     * distance to move by
     * @return new Camera instance
     */
    fun backward(speed: Double): Camera {
        return forward(-speed)
    }

    /**
     * Returns a new camera moved in the negative direction of z-axis by the
     * given distance
     *
     * @param speed
     * distance to move by
     * @return new Camera instance
     */
    fun down(speed: Double): Camera {
        return up(-speed)
    }

    /**
     * Returns a new camera moved in the direction of the view vector by the
     * given distance
     *
     * @param speed
     * distance to move by
     * @return new Camera instance
     */
    fun forward(speed: Double): Camera {
        return Camera(this, position.add(viewVector.mul(speed)))
    }

    /**
     * Returns a new camera moved in the opposite direction of a cross product
     * of the view vector and the up vector, i.e. to the left from the
     * observer's perspective, by the given distance
     *
     * @param speed
     * distance to move by
     * @return new Camera instance
     */
    fun left(speed: Double): Camera {
        return right(-speed)
    }

    /**
     * Returns a new camera moved by the given vector
     *
     * @param dir
     * vector to move by
     * @return new Camera instance
     */
    fun move(dir: Vec3D?): Camera {
        return Camera(this, position.add(dir!!))
    }

    /**
     * Returns a new camera with radius multiplied by the given coefficient.
     * Radius is kept >= 0.1
     *
     * @param scale
     * radius scale coefficient
     * @return new Camera instance
     */
    fun mulRadius(scale: Double): Camera {
        return Camera(this, kotlin.math.max(radius * scale, 0.1))
    }

    /**
     * Returns a new camera moved in the direction of a cross product of the
     * view vector and the up vector, i.e. to the right from the observer's
     * perspective, by the given distance
     *
     * @param speed
     * distance to move by
     * @return new Camera instance
     */
    fun right(speed: Double): Camera {
        return Camera(
            this, position.add(
                Vec3D(
                    kotlin.math.cos(
                        azimuth
                                - kotlin.math.PI / 2
                    ) as Double, kotlin.math.sin(azimuth - kotlin.math.PI / 2) as Double, 0.0
                )
                    .mul(speed)
            )
        )
    }

    /**
     * Returns a new camera moved in the direction of z-axis by the given
     * distance
     *
     * @param speed
     * distance to move by
     * @return new Camera instance
     */
    fun up(speed: Double): Camera {
        return Camera(this, position.add(Vec3D(0.0, 0.0, speed)))
    }

    /**
     * Returns a new camera with azimuth set to the given value
     *
     * @param ang
     * new azimuth value
     * @return new Camera instance
     */
    fun withAzimuth(ang: Double): Camera {
        return Camera(this, ang, zenith)
    }

    /**
     * Returns a new camera with 1st/3rd person camera mode flag set to the
     * given value
     *
     * @param firstPerson
     * boolean flag indicating 1st (true) / 3rd (false) person camera
     * mode
     * @return new Camera instance
     */
    fun withFirstPerson(firstPerson: Boolean): Camera {
        return Camera(this, firstPerson)
    }

    /**
     * Returns a new camera with position set to the given vector
     *
     * @param pos
     * new position
     * @return new Camera instance
     */
    fun withPosition(pos: Vec3D): Camera {
        return Camera(this, pos)
    }

    /**
     * Returns a new camera with radius (the distance between the eye (camera)
     * and the observer in 3rd person camera mode) set to the given value
     *
     * @param radius
     * new radius value
     * @return new Camera instance
     */
    fun withRadius(radius: Double): Camera {
        return Camera(this, radius)
    }

    /**
     * Returns a new camera with zenith set to the given value
     *
     * @param ang
     * new zenith value
     * @return new Camera instance
     */
    fun withZenith(ang: Double): Camera {
        return Camera(this, azimuth, ang)
    }


    init {
        val upVector = Vec3D(
            (kotlin.math.cos(azimuth) * kotlin.math.cos(zenith + kotlin.math.PI / 2)),
            (kotlin.math.sin(azimuth) * kotlin.math.cos(zenith + kotlin.math.PI / 2)),
            kotlin.math.sin(zenith + kotlin.math.PI / 2)
        )
        if (firstPerson) {
            eye = Vec3D(position)
            viewMatrix = Mat4ViewRH(position, viewVector.mul(radius), upVector)
        } else {
            eye = position.add(viewVector.mul(-radius))
            viewMatrix = Mat4ViewRH(eye, viewVector.mul(radius), upVector)
        }
    }
}