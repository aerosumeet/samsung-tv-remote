package com.example.samsungtvremote

import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var irManager: ConsumerIrManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        irManager = getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager?

        if (irManager == null || !irManager!!.hasIrEmitter()) {
            Toast.makeText(this, "Error: Device lacks a built-in IR Blaster!", Toast.LENGTH_LONG).show()
        }

        setupButtons()
    }

    private fun setupButtons() {
        bindIrButton(R.id.btnPower, SamsungIrEncoder.CMD_POWER)
        bindIrButton(R.id.btnVolUp, SamsungIrEncoder.CMD_VOL_UP)
        bindIrButton(R.id.btnVolDown, SamsungIrEncoder.CMD_VOL_DOWN)
        bindIrButton(R.id.btnMute, SamsungIrEncoder.CMD_MUTE)
        bindIrButton(R.id.btnChUp, SamsungIrEncoder.CMD_CH_UP)
        bindIrButton(R.id.btnChDown, SamsungIrEncoder.CMD_CH_DOWN)
        bindIrButton(R.id.btnNavUp, SamsungIrEncoder.CMD_NAV_UP)
        bindIrButton(R.id.btnNavDown, SamsungIrEncoder.CMD_NAV_DOWN)
        bindIrButton(R.id.btnNavLeft, SamsungIrEncoder.CMD_NAV_LEFT)
        bindIrButton(R.id.btnNavRight, SamsungIrEncoder.CMD_NAV_RIGHT)
        bindIrButton(R.id.btnNavOk, SamsungIrEncoder.CMD_NAV_OK)
        bindIrButton(R.id.btnBack, SamsungIrEncoder.CMD_BACK)
        bindIrButton(R.id.btnExit, SamsungIrEncoder.CMD_EXIT)
        bindIrButton(R.id.btnHome, SamsungIrEncoder.CMD_HOME)
    }

    private fun bindIrButton(buttonId: Int, command: Int) {
        findViewById<Button>(buttonId).setOnClickListener {
            transmitIr(command)
        }
    }

    private fun transmitIr(command: Int) {
        val manager = irManager
        if (manager != null && manager.hasIrEmitter()) {
            val pattern = SamsungIrEncoder.buildPattern(command)
            manager.transmit(SamsungIrEncoder.CARRIER_FREQUENCY, pattern)
        } else {
            Toast.makeText(this, "IR Emitter unavailable", Toast.LENGTH_SHORT).show()
        }
    }
}
