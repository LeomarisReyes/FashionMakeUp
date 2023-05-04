package lr.projects.fashionmakeupapp.ui.makeuplist.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

import lr.projects.fashionmakeupapp.R
import lr.projects.fashionmakeupapp.model.Product

@Composable
fun CardHeader(
    name:String,
    brand:String?,
    modifier: Modifier = Modifier,
){
    Row(modifier = modifier.padding(10.dp)) {
        Box(modifier = modifier
            .clip(CircleShape)
            .background(color = Color.LightGray)
            .size(40.dp)) {
            Text(text = name[0].toString(),
                color = colorResource(id = R.color.darkGray),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(10.dp))
        }
        Column(modifier = modifier.padding(start = 10.dp)) {
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp)

            Text(
                buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Brand: ") }
                    append(brand?:"-") },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun LoadingAnimation(){
    Column(verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
        , horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Loading data",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.girl))
        LottieAnimation(composition)
    }
}