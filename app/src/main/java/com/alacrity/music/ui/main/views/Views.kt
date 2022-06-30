package com.alacrity.music.ui.main.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alacrity.music.theme.ColorButtonDefault
import com.alacrity.music.theme.TemplateTypography

@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    style: androidx.compose.ui.text.TextStyle = TemplateTypography.h1
) {
    Text(text = text, modifier = modifier, textAlign = textAlign, style = style)
}

@Composable
fun ImageWithTextMainPage(@DrawableRes drawable: Int, text: String, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
        Image(
            painter = painterResource(drawable), contentDescription = null, modifier = Modifier
                .size(190.dp)
                .padding(top = 0.dp)
                .clickable { onClick() }
        )

        SimpleText(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            style = TemplateTypography.h2
        )
    }
}

@Composable
fun BackButton(modifier: Modifier = Modifier, onBackPress: () -> Unit) {
    Button(
        onClick = { onBackPress() },
        shape = RoundedCornerShape(40),
        colors = ButtonDefaults.buttonColors(backgroundColor = ColorButtonDefault),
        modifier = modifier.padding(start = 10.dp, top = 10.dp)
    ) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
    }
}

@Composable
fun ImagesTransferRow(@DrawableRes image1: Int, @DrawableRes image2: Int, @DrawableRes arrow: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(image1), contentDescription = null, modifier = Modifier
                .size(150.dp)
                .padding(top = 0.dp)
        )
        Image(
            painter = painterResource(arrow), contentDescription = null, modifier = Modifier
                //   .size(200.dp)
                .padding(top = 30.dp)
        )
        Image(
            painter = painterResource(image2), contentDescription = null, modifier = Modifier
                .size(150.dp)
                .padding(top = 0.dp)
        )
    }
}

@Composable
fun ConvertButton(modifier: Modifier = Modifier, text: String, onConvertClick: () -> Unit) {
    Button(
        onClick = { onConvertClick() },
        shape = RoundedCornerShape(40),
        colors = ButtonDefaults.buttonColors(backgroundColor = ColorButtonDefault),
        modifier = modifier.padding(start = 10.dp, top = 10.dp).width(250.dp).height(50.dp)
    ) {
        SimpleText(text = text, style = TemplateTypography.h2)
    }
}

@Composable
fun ConvertScreenBottomBlock(convertTo: String, fileName: String, onConvertClick: () -> Unit) {
    Column(modifier = Modifier.padding(bottom = 70.dp)) {
        ConvertButton(text = "Convert to $convertTo", modifier = Modifier.align(CenterHorizontally)) {
            onConvertClick()
        }
        SimpleText(text = fileName, modifier = Modifier.fillMaxWidth().padding(top = 20.dp))
    }
}

@Composable
fun ResultScreenInfoCard(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp, bottom = 80.dp)
            .clickable {
                       onClick()
            },
        backgroundColor = ColorButtonDefault,
        shape = RoundedCornerShape(40)
    ) {
        SimpleText(text = text, modifier = Modifier.padding(all = 7.dp))
    }
}

