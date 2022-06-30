package com.alacrity.music.ui.main

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alacrity.music.App
import com.alacrity.music.exceptions.BaseException
import com.alacrity.music.TemplateApp
import com.alacrity.music.bus.MainBus
import com.alacrity.music.bus.MainBusEvent
import com.alacrity.music.exceptions.InvalidFileException
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainBus: MainBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        subscribeOnBus()
        setContent {
            TemplateApp(context = this, homeViewModel = mainViewModel)
        }
    }

    private fun subscribeOnBus() {
        lifecycleScope.launchWhenCreated {
            mainBus.dataFlow.collect {
                Timber.d("OnCollectMainActivity $it")
                when (it) {
                    is MainBusEvent.OpenDownloadsFolder -> openDownloadsFolder()
                    is MainBusEvent.PickUpFile -> pickUpFile(it.ext)
                    is MainBusEvent.ToastAlert -> Toast.makeText(this@MainActivity, it.text, Toast.LENGTH_LONG).show()
                    else -> Unit
                }
            }
        }
    }

    private fun pickUpFile(ext: String) {
        Timber.d("Picking up a file")
        val intentType = if (ext == "pdf") "application/pdf" else "*/*"
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = intentType
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, PICKFILE_REQUEST_CODE)
    }

    private fun openDownloadsFolder() {
        val intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val file =
            if (data == null) Result.failure(
                InvalidFileException("Choose valid file")
            ) else Result.success(
                File(data.dataString.toString())
            )
        mainBus.insertValue(MainBusEvent.OnFileUploaded(file))
        super.onActivityResult(requestCode, resultCode, data)
    }



    companion object {
        const val PICKFILE_REQUEST_CODE = 42
    }

}