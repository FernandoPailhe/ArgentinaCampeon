package com.ferpa.argentinacampeon.domain.businesslogic

import android.content.Context
import android.content.Intent
import com.ferpa.argentinacampeon.common.Extensions.toSharingUri
import com.ferpa.argentinacampeon.domain.repository.PhotoRepository
import java.io.File
import javax.inject.Inject

class ShareImageUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(imageFile: File, context: Context, text: String? = null) {

        val photoUriArray = arrayListOf(imageFile.toSharingUri(context))

        val sendIntent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, photoUriArray)
            type = "image/*"
            putExtra(Intent.EXTRA_TEXT, text ?: "Hola")
            type = "image/png"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

}