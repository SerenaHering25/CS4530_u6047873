package com.example.a5marblegame

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MarbleState(val x: Float = 0f, val y: Float = 0f)


// Holds the marble's position/velocity and runs the physics.

class MarbleViewModel : ViewModel() {

    private val _state = MutableStateFlow(MarbleState())
    val state: StateFlow<MarbleState> = _state.asStateFlow()

    // Velocity in dp per second
    private var velocityX = 0f
    private var velocityY = 0f

    // Largest allowed offset (screen size minus marble size), in dp
    private var maxX = 0f
    private var maxY = 0f

    // Timestamp (ns) of the previous sensor event, used to compute dt
    private var lastTimestamp = 0L

    // Called by the UI once BoxWithConstraints knows how big the screen is.
    fun setBounds(maxX: Float, maxY: Float) {
        val firstTime = this.maxX == 0f && this.maxY == 0f
        this.maxX = maxX.coerceAtLeast(0f)
        this.maxY = maxY.coerceAtLeast(0f)

        _state.value = if (firstTime) {
            // Start the marble in the middle of the screen
            MarbleState(this.maxX / 2f, this.maxY / 2f)
        } else {
            // Screen size changed, keep the marble inside the new limits
            MarbleState(
                _state.value.x.coerceIn(0f, this.maxX),
                _state.value.y.coerceIn(0f, this.maxY)
            )
        }
    }

     //  Called for every sensor reading.
    fun onGravity(gravityX: Float, gravityY: Float, timestampNs: Long) {
        if (lastTimestamp == 0L) {
            lastTimestamp = timestampNs
            return
        }
        // Seconds since the last reading. Capped so a long pause doesn't make the marble jump.
        val dt = ((timestampNs - lastTimestamp) / 1_000_000_000f).coerceIn(0f, MAX_DT)
        lastTimestamp = timestampNs

        if (maxX <= 0f || maxY <= 0f) return   // screen size not known yet

        // velocity += dt * scale * gravity
        //  - Sensor x: tilting the right edge down makes gravityX negative, but the marble
        //    should roll right (+x on screen), so the x scale is negative.
        //  - Sensor y points up the screen, offset y points down. Tilting the top edge
        //    down makes gravityY negative and the marble should roll up (−y on screen),
        //    so the y scale is positive.
        velocityX += dt * -ACCEL_SCALE * gravityX
        velocityY += dt * ACCEL_SCALE * gravityY

        // lose a fraction of speed every second
        val damping = (1f - FRICTION * dt).coerceIn(0f, 1f)
        velocityX *= damping
        velocityY *= damping

        // position += dt * velocity
        var x = _state.value.x + dt * velocityX
        var y = _state.value.y + dt * velocityY

        // Keep the marble on screen; bounce softly off the walls
        if (x < 0f) {
            x = 0f; velocityX = -velocityX * BOUNCE
        } else if (x > maxX) {
            x = maxX; velocityX = -velocityX * BOUNCE
        }
        if (y < 0f) {
            y = 0f; velocityY = -velocityY * BOUNCE
        } else if (y > maxY) {
            y = maxY; velocityY = -velocityY * BOUNCE
        }

        _state.value = MarbleState(x, y)
    }

    // Called when the sensor stops so the next dt starts fresh.
    fun resetClock() {
        lastTimestamp = 0L
    }

    companion object {
        const val ACCEL_SCALE = 120f  // dp/s² per m/s² of gravity
        const val FRICTION = 1.2f     // fraction of speed lost per second
        const val BOUNCE = 0.3f
        const val MAX_DT = 0.05f
    }
}