package com.example.rickmorty_mvvm_app.utils

class ResponseBodyNullException(place: String, message: String = "Response is null in $place") : Exception(message)

class FailureResponseException(place: String, message: String = "Response is failing in $place") : Exception(message)