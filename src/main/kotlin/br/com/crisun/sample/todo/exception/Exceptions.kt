package br.com.crisun.sample.todo.exception

class ValidationException(message: String) : Exception(message)

class NotFoundException(message: String? = null) : Exception(message)
