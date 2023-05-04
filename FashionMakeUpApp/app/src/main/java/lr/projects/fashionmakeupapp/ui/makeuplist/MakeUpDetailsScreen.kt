@file:OptIn(ExperimentalMaterial3Api::class)

package lr.projects.fashionmakeupapp.ui.makeuplist

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import lr.projects.fashionmakeupapp.R
import lr.projects.fashionmakeupapp.model.Product
import lr.projects.fashionmakeupapp.model.ProductColor
import lr.projects.fashionmakeupapp.ui.makeuplist.utils.CardHeader
import lr.projects.fashionmakeupapp.ui.makeuplist.utils.LoadingAnimation

@Composable
fun MakeUpDetailsScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    productId: String?,
    viewModel: MakeUpDetailsViewModel = hiltViewModel(),
) {
    val state = viewModel.viewState

    LaunchedEffect(Unit){
        viewModel.getProduct(productId?: "")
    }

    EffectHandler(
        effectFlow = viewModel.effect,
        snackbarHostState = snackbarHostState,
        navController = navController
    )

    MakeUpDetailsScreen(state.value, onUiEvent = { event ->
        viewModel.setEvent(event)})
}


@Composable
fun AvailableColors(
    productItemColor : List<ProductColor>,
    modifier: Modifier = Modifier){

    if(productItemColor?.isEmpty() == true){
        Text(text = "No colors available",
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp))
    } else {

        val griSize = if(productItemColor.size % 2 == 1){
            ((productItemColor.size + 1)/2) * 36.7.dp
        }else{
            (productItemColor.size/2) * 36.7.dp
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxWidth()
                .size(griSize)
                .padding(start = 12.dp, end = 12.dp, top = 15.dp)) {

            items(items = productItemColor) { item ->
                Row(modifier = modifier.padding(bottom = 10.dp)) {
                    Box(
                        modifier = modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .clip(CircleShape)
                            .background(color = Color(parseColor(item.hex_value)))
                            .size(25.dp)
                    )
                    Text(text = item.colour_name.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}


@Composable
private fun EffectHandler(
    effectFlow: Flow<MakeUpDetailsViewModel.UiEffect>,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val rememberedFlow = remember(effectFlow, lifecycleOwner) {
        effectFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    LaunchedEffect(rememberedFlow) {
        rememberedFlow.collectLatest { effect ->
            when (effect) {
                MakeUpDetailsViewModel.UiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        "An error occurred",
                        duration = SnackbarDuration.Short
                    )
                }
                is MakeUpDetailsViewModel.UiEffect.GoBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun MakeUpDetailsScreen(
    state: MakeUpDetailsViewModel.UiState,
    onUiEvent: (MakeUpDetailsViewModel.UiEvent) -> Unit
) {

    if (state.isLoading) {
        LoadingAnimation()
    } else { 
        MakeUpProductItem(product = state.makeUpProduct, onUiEvent = onUiEvent)
    }
}

@Composable
fun BasicInformation(name: String,
                     description: String,
                     price: String){
    Text(
        text = name,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(start = 22.dp)
    )

    Text(
        text = description ?: "No description available",
        fontSize = 14.sp,
        textAlign = TextAlign.Justify,
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 25.dp),
    )
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, end = 20.dp)
    ) {
        Text(
            text = "Price:  $price",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ChipList( productItemTags : List<String>?){

    if(productItemTags?.isEmpty() == true){
        Text(text = "No tags available",
            modifier = Modifier.padding(start = 20.dp, top = 10.dp))
    } else {
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        )
        {
            productItemTags?.let {
                items(items = productItemTags) { item ->
                    AssistChip(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        onClick = {  } ,
                        label = { Text(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun OverlappingElements(
    content: @Composable () -> Unit
) {
    Layout(content = content) { measurables, constraints ->

        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val image = measurables[0].measure(looseConstraints)
        val card = measurables[1].measure(looseConstraints)

        layout(
            width = constraints.maxWidth,
            height = image.height + card.height / 2,
        ) {
            image.placeRelative(x = 0, y = 0)
            card.placeRelative(
                x = (constraints.maxWidth - card.width) / 2,
                y = image.height - card.height / 2
            )
        }
    }
}

@Composable
fun MakeUpProductItem(
    product: Product,
    onUiEvent: (MakeUpDetailsViewModel.UiEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        OverlappingElements() {
            CoilImage(
                imageModel = product.image_link,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(R.drawable.notfound),
                placeHolder = ImageBitmap.imageResource(R.drawable.comingsoon),
                modifier = Modifier
                    .height(300.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, bottom = 20.dp)
                    .height(75.dp) ,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
            ) {
                CardHeader(product.name, product.brand)
            }

        }
        BasicInformation(name = product.name, description = product.description?:"", price = "${product.price_sign}${product.price} ${product.currency}")

        Divider(
            color = colorResource(id = R.color.gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp))

        Text(
            text = "Available Colors",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 20.dp))

        AvailableColors(productItemColor = product.product_colors!!)

        Text(
            text = "Tags",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 20.dp , top = 20.dp))

            ChipList(product.tag_list)

            Button(shape = RoundedCornerShape(20.dp),
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(20.dp)
                 .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.darkPink)),
            onClick = {
               onUiEvent(MakeUpDetailsViewModel.UiEvent.GoBack)
            }
            ) {
                Text(
                    text = "Go Back",
                    color = colorResource(id = R.color.white)
                )
            }
    }
}
