package com.alacrity.music.exceptions

open class BaseException(message: String = "Undefined", exception: Throwable? = null): Exception(message, exception)