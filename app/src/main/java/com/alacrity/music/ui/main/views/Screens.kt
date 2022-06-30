package com.alacrity.music.ui.main.views

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alacrity.music.R
import com.alacrity.music.theme.ColorDefaultBackground
import com.alacrity.music.theme.gradientBackgroundBrush
import com.alacrity.music.ui.main.models.MainDataState
import com.alacrity.music.ui.main.models.getFile

@Composable
fun HomeScreen(onItemClick: (String) -> Unit) {
    Box(modifier = Modifier.background(brush = gradientBackgroundBrush)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = CenterHorizontally
        ) {

            ImageWithTextMainPage(drawable = R.drawable.ic_word, text = "Load word file") {
                onItemClick("WordToPdf")
            }

            ImageWithTextMainPage(drawable = R.drawable.ic_pdf, text = "Load pdf file") {
                onItemClick("PdfToWord")
            }
        }
    }
}

@Composable
fun ConvertationScreen(
    way: String,
    fileName: String,
    onBackClick: () -> Unit,
    onConvertClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackgroundBrush),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton { onBackClick() }

        if (way == "WordToPdf")
            ImagesTransferRow(
                image1 = R.drawable.ic_word,
                image2 = R.drawable.ic_pdf,
                arrow = R.drawable.ic_arrow_blue
            )
        else
            ImagesTransferRow(
                image1 = R.drawable.ic_pdf,
                image2 = R.drawable.ic_word,
                arrow = R.drawable.ic_arrow_red
            )

        val convertTo = if (way == "WordToPdf") "Pdf" else "Word"

        ConvertScreenBottomBlock(convertTo = convertTo, fileName = fileName) {
            onConvertClick()
        }

    }

    BackHandler {
        onBackClick()
    }
}

@Composable
fun ErrorScreen(error: Throwable? = null, message: String? = null) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
        Column {
            error?.let {
                SimpleText(text = it.stackTraceToString())
            }
            message?.let {
                SimpleText(text = it)
            }
        }
    }
}

@Composable
fun ConvertingFileResultScreen(fileState: MainDataState, onBackClick: () -> Unit, onOpenDownloadsFolder: () -> Unit) {
    val file = fileState.getFile()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackgroundBrush),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth())  {
            BackButton { onBackClick() }
        }
        if (file != null) {
            Image(
                painter = painterResource(R.drawable.ic_success),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 0.dp)
                    .clickable {
                        onBackClick()
                    }
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 0.dp)
                    .clickable {
                        onBackClick()
                    }
            )
        }

        if (file != null) {
            ResultScreenInfoCard(text = "File successfully converted & saved to your downloads(Tap to open downloads folder)") {
                onOpenDownloadsFolder()
            }
        } else {
            ResultScreenInfoCard(text = "Error loading file, it may be corrupted") {

            }
        }
    }

    BackHandler {
        onBackClick()
    }
}