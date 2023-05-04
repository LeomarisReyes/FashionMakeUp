package lr.projects.fashionmakeupapp.ui.makeuplist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import lr.projects.fashionmakeupapp.R
import lr.projects.fashionmakeupapp.model.Product
import lr.projects.fashionmakeupapp.ui.makeuplist.MakeUpListViewModel.UiEffect
import lr.projects.fashionmakeupapp.ui.makeuplist.utils.CardHeader
import lr.projects.fashionmakeupapp.ui.makeuplist.utils.LoadingAnimation

@Composable
fun MakeUpListScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: MakeUpListViewModel = hiltViewModel(),
) {
    val state = viewModel.viewState

    EffectHandler(
        effectFlow = viewModel.effect,
        snackbarHostState = snackbarHostState,
        onNavigate = { route ->
            navController.navigate(route)
        }
    )

    MakeUpListScreen(state.value, onUiEvent = { event ->
        viewModel.setEvent(event)
    })
}

@Composable
private fun EffectHandler(
    effectFlow: Flow<UiEffect>,
    snackbarHostState: SnackbarHostState,
    onNavigate: (String) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val rememberedFlow = remember(effectFlow, lifecycleOwner) {
        effectFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    LaunchedEffect(rememberedFlow) {
        rememberedFlow.collectLatest { effect ->
            when (effect) {
                UiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        "An error occurred",
                        duration = SnackbarDuration.Short
                    )
                }
                is UiEffect.Navigate -> onNavigate(effect.route)
            }
        }
    }
}

@Composable
fun MakeUpListScreen(
    state: MakeUpListViewModel.UiState,
    onUiEvent: (MakeUpListViewModel.UiEvent) -> Unit
) {
     if (state.isLoading) {
        LoadingAnimation()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
        )
        {
            items(items = state.makeUpList, key = { it.id }) { item ->
                MakeUpItem(product = item, onUiEvent = onUiEvent)
            }
        }
    }
}


@Composable
fun Pricing(price: String){
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 15.dp)
    ) {
        Text(
            text = price,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Icon(
            Icons.Rounded.ArrowForward,
            contentDescription = "Arrow"
        )
    }
}

@Composable
fun MakeUpItem(
    product: Product,
    modifier: Modifier = Modifier,
    onUiEvent: (MakeUpListViewModel.UiEvent) -> Unit
) {

    Card(modifier = modifier
        .fillMaxSize()
        .height(340.dp)
        .padding(start = 5.dp, end = 5.dp, top = 8.dp, bottom = 3.dp)
        .clickable {
            onUiEvent(MakeUpListViewModel.UiEvent.ItemSelected(product.id))
        }, elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
            )
    ) {
        CardHeader(product.name,product.brand)

        CoilImage(
            imageModel = product.image_link,
            contentDescription = "mainimage",
            modifier = modifier
                .fillMaxWidth()
                .height(165.dp),
            error = ImageBitmap.imageResource(R.drawable.notfound),
            placeHolder = ImageBitmap.imageResource(R.drawable.comingsoon),
            contentScale = ContentScale.Crop
        )

        Text(
            text = product.description ?: "Not available",
            modifier = modifier
                .padding(15.dp),
            fontSize = 14.sp,
            textAlign = TextAlign.Justify,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Pricing(price = "${product.price_sign}${product.price} ${product.currency}")
    }
}
