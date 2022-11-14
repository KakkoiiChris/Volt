package innovolt.volt.linker

import innovolt.volt.runtime.Result

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    LinkData.kt
 *
 * Created: Saturday, November 12, 2022, 22:33:24
 *
 * @author Christian Bryce Alexander
 */
data class LinkData(val instance: Result.Instance?, val args: List<Result<*>>)