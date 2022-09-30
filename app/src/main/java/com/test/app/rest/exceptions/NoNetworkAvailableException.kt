package com.test.app.rest.exceptions

import java.io.IOException

class NoNetworkAvailableException(message: String): IOException(message)