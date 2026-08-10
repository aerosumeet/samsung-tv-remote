package com.example.samsungtvremote

object SamsungIrEncoder {
    const val CARRIER_FREQUENCY = 38000 // 38 kHz

    // Samsung IR Hex Commands (Custom Code Address: 0x0707)
    const val CMD_POWER = 0x02
    const val CMD_VOL_UP = 0x07
    const val CMD_VOL_DOWN = 0x0B
    const val CMD_MUTE = 0x0F
    const val CMD_CH_UP = 0x12
    const val CMD_CH_DOWN = 0x10
    const val CMD_NAV_UP = 0x60
    const val CMD_NAV_DOWN = 0x61
    const val CMD_NAV_LEFT = 0x65
    const val CMD_NAV_RIGHT = 0x62
    const val CMD_NAV_OK = 0x68
    const val CMD_BACK = 0x58    // Return / Back key
    const val CMD_EXIT = 0x1F    // Smart Hub Exit key (Updated from 0x2B)
    const val CMD_HOME = 0x79    // Smart Hub / Home key

    fun buildPattern(commandByte: Int): IntArray {
        val customAddr1 = 0x07
        val customAddr2 = 0x07
        val invCommand = commandByte.inv() and 0xFF

        val bytes = intArrayOf(customAddr1, customAddr2, commandByte, invCommand)
        val pattern = ArrayList<Int>()

        // Header: 4500us Mark, 4500us Space
        pattern.add(4500)
        pattern.add(4500)

        // 32 bits (LSB first per byte)
        for (b in bytes) {
            for (bit in 0..7) {
                val isOne = (b and (1 shl bit)) != 0
                pattern.add(560) // Mark
                if (isOne) {
                    pattern.add(1690) // Logical 1
                } else {
                    pattern.add(560)  // Logical 0
                }
            }
        }

        // Stop bit
        pattern.add(560)

        return pattern.toIntArray()
    }
}
