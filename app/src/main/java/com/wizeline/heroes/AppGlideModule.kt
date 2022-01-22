package com.wizeline.heroes

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class AppGlideModule : AppGlideModule() {
    /**
     * Opciones de Glide de configuración global
     */
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        // Por ejemplo: establezca el formato de imagen globalmente como RGB_565
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
    }
    /**
     * Registrar componentes personalizados
     */
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }

    fun RequestOptions.miniThumb(size: Int): RequestOptions {
        return this.fitCenter()
            .override(100)
    }
}
