package innovolt.volt.linker

import innovolt.volt.runtime.VoltValue

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
data class LinkData(val instance: VoltValue.Instance?, val args: List<VoltValue<*>>)