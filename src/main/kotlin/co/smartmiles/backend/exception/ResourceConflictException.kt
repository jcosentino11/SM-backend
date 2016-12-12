package co.smartmiles.backend.exception

/**
 * @author Joseph Cosentino.
 */
class ResourceConflictException : RuntimeException {

    constructor() : super()

    constructor(message : String) : super(message)

    constructor(cause: Exception) : super(cause)

    constructor(message: String, cause:Exception) : super(message, cause)

}