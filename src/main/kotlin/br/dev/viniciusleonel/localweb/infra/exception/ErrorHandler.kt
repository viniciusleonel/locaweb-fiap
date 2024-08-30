package br.dev.viniciusleonel.localweb.infra.exception

import br.dev.viniciusleonel.localweb.dto.ErrorDTO
import jakarta.persistence.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.function.Consumer

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleErrorEntityNotFound(ex: EntityNotFoundException): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body<Any>(ErrorDTO(ex.message))
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleErrorDataIntegrityViolation(ex: DataIntegrityViolationException): ResponseEntity<*> {
        val rootCause = ex.rootCause?.message

        val errorMessage = when {
            rootCause?.contains("Duplicate entry") == true -> {
                val duplicatedField = when {
                    rootCause.contains("UK6dotkott2kjsp8vw4d0m25fb7") -> "email"
                    rootCause.contains("UKr43af9ap4edm43mmtq01oddj6") -> "username"
                    else -> "unknown"
                }
                "Duplicate value found for $duplicatedField"
            }
            else -> "Data integrity violation occurred"
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body<Any>(ErrorDTO(errorMessage))
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): ResponseEntity<*> {
        return ResponseEntity
            .status(ex.status)
            .body<Any>(ErrorDTO(ex.message))
    }


    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleErrorDataIntegrityViolation(ex: HttpMessageNotReadableException): ResponseEntity<*> {
        val error = "Formato Json Inválido."
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body<Any>(ErrorDTO(error))
    }



}