/*
 * Copyright 2019 New Vector Ltd
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

package im.vector.app.features.html

import android.content.Context
import android.text.Spannable
import androidx.core.text.toSpannable
import im.vector.app.core.resources.ColorProvider
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import org.commonmark.node.Node
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventHtmlRenderer @Inject constructor(htmlConfigure: MatrixHtmlPluginConfigure,
                                            context: Context) {

    interface PostProcessor {
        fun afterRender(renderedText: Spannable)
    }

    private val markwon = Markwon.builder(context)
            .usePlugin(HtmlPlugin.create(htmlConfigure))
            .build()

    fun parse(text: String): Node {
        return markwon.parse(text)
    }

    /**
     * @param text the text you want to render
     * @param postProcessors an optional array of post processor to add any span if needed
     */
    fun render(text: String, vararg postProcessors: PostProcessor): CharSequence {
        return try {
            val parsed = markwon.parse(text)
            renderAndProcess(parsed, postProcessors)
        } catch (failure: Throwable) {
            Timber.v("Fail to render $text to html")
            text
        }
    }

    /**
     * @param node the node you want to render
     * @param postProcessors an optional array of post processor to add any span if needed
     */
    fun render(node: Node, vararg postProcessors: PostProcessor): CharSequence? {
        return try {
            renderAndProcess(node, postProcessors)
        } catch (failure: Throwable) {
            Timber.v("Fail to render $node to html")
            return null
        }
    }

    private fun renderAndProcess(node: Node, postProcessors: Array<out PostProcessor>): CharSequence {
        val renderedText = markwon.render(node).toSpannable()
        postProcessors.forEach {
            it.afterRender(renderedText)
        }
        return renderedText
    }
}

class MatrixHtmlPluginConfigure @Inject constructor(private val colorProvider: ColorProvider) : HtmlPlugin.HtmlConfigure {

    override fun configureHtml(plugin: HtmlPlugin) {
        plugin
                .addHandler(FontTagHandler())
                .addHandler(MxReplyTagHandler())
                .addHandler(SpanHandler(colorProvider))
    }
}
