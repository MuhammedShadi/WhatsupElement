/*
 * Copyright (c) 2020 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.features.media

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import im.vector.app.R
import im.vector.app.databinding.MergeImageAttachmentOverlayBinding
import im.vector.lib.attachmentviewer.AttachmentEventListener
import im.vector.lib.attachmentviewer.AttachmentEvents

class AttachmentOverlayView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), AttachmentEventListener {

    var onShareCallback: (() -> Unit)? = null
    var onBack: (() -> Unit)? = null
    var onPlayPause: ((play: Boolean) -> Unit)? = null
    var videoSeekTo: ((progress: Int) -> Unit)? = null

    val views: MergeImageAttachmentOverlayBinding

    var isPlaying = false

    var suspendSeekBarUpdate = false

    init {
        inflate(context, R.layout.merge_image_attachment_overlay, this)
        views = MergeImageAttachmentOverlayBinding.bind(this)
        setBackgroundColor(Color.TRANSPARENT)
        views.overlayBackButton.setOnClickListener {
            onBack?.invoke()
        }
        views.overlayShareButton.setOnClickListener {
            onShareCallback?.invoke()
        }
        views.overlayPlayPauseButton.setOnClickListener {
            onPlayPause?.invoke(!isPlaying)
        }

        views.overlaySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    videoSeekTo?.invoke(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                suspendSeekBarUpdate = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                suspendSeekBarUpdate = false
            }
        })
    }

    fun updateWith(counter: String, senderInfo: String) {
        views.overlayCounterText.text = counter
        views.overlayInfoText.text = senderInfo
    }

    override fun onEvent(event: AttachmentEvents) {
        when (event) {
            is AttachmentEvents.VideoEvent -> {
                views.overlayPlayPauseButton.setImageResource(if (!event.isPlaying) R.drawable.ic_play_arrow else R.drawable.ic_pause)
                if (!suspendSeekBarUpdate) {
                    val safeDuration = (if (event.duration == 0) 100 else event.duration).toFloat()
                    val percent = ((event.progress / safeDuration) * 100f).toInt().coerceAtMost(100)
                    isPlaying = event.isPlaying
                    views.overlaySeekBar.progress = percent
                }
            }
        }
    }
}
