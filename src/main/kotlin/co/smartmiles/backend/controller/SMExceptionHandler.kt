package co.smartmiles.backend.controller

import co.smartmiles.backend.exception.BadRequestException
import co.smartmiles.backend.exception.InternalServerErrorException
import co.smartmiles.backend.exception.NotFoundException
import co.smartmiles.backend.exception.ResourceConflictException
import co.smartmiles.backend.model.Error
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

/**
 * @author Joseph Cosentino.
 */
@ControllerAdvice
@ResponseBody
open class SMExceptionHandler {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(e : BadRequestException) : Error {
        logger.info("Bad Request", e)
        return Error(e.message ?: "Bad Request", 400)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(e : MethodArgumentTypeMismatchException) : Error {
        logger.info("Bad Request", e)
        return Error("Bad Request", 400)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(e : HttpMessageNotReadableException) : Error {
        logger.info("Bad Request", e)
        return Error("Bad Request", 400)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handle(e : NotFoundException) : Error {
        logger.info("Resource not found", e)
        return Error(e.message ?: "Resource not found", 404)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    fun handle(e : HttpMediaTypeNotAcceptableException) {
        logger.info("Media type not acceptable", e)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    fun handle(e: HttpMediaTypeNotSupportedException){
        logger.info("Media type not supported", e)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handle(e : ResourceConflictException) : Error {
        logger.error("Internal Server Error", e)
        return Error("Resource conflict exception", 409)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun handle(e : HttpRequestMethodNotSupportedException) : Error {
        logger.info("Method not supported", e)
        return Error("Method not supported", 405)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handle(e : InternalServerErrorException) : Error {
        logger.error("Internal Server Error", e)
        return Error("Internal Server Error", 500)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handle(e : Exception) : Error {
        logger.error("Internal Server Error", e)
        return Error("Internal Server Error", 500)
    }




}