package br.com.crisun.sample.ktor.exception

import io.ktor.http.HttpStatusCode

open class BaseHttpException(message: String, val httpStatus : Int, throwable: Throwable?) : Exception(message, throwable)

class ValidationException(message: String) : BaseHttpException(message, HttpStatusCode.BadRequest.value, null)