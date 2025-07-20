package com.example.neumorphism.sample1

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Anchor
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.WavyProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neumorphism.ui.theme.AppTheme
import com.example.neumorphism.ui.theme.NeumorphismTheme
import com.example.neumorphism.ui.theme.Purple200
import com.example.neumorphism.ui.theme.Purple500
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.NeuAttrs
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.Pressed
import com.gandiva.neumorphic.shape.RoundedCorner
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(){
    val systemUiController = rememberSystemUiController()
    var isDarkTheme by remember { mutableStateOf(false) }
    NeumorphismTheme(
        darkTheme = isDarkTheme
    ) {
        val color= AppTheme.customColors.background
        SideEffect {
            systemUiController.setStatusBarColor(
                color =color,
                darkIcons = !isDarkTheme
            )
        }
        Scaffold (
            topBar = {
                CustomTopbar(
                    isDarkTheme=isDarkTheme
                ) {
                    isDarkTheme=it
                }
            }
        ){ innerPadding->
            val focusManager=LocalFocusManager.current
            Column (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(color = AppTheme.customColors.background)
                    .clickable(
                        indication = null,
                        interactionSource =  remember { MutableInteractionSource() }
                    ){
                        focusManager.clearFocus()
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                CustomTextFields()
                Row{
                    CustomCheckBox(neuAttrs = defaultPressedNeuAttrs())
                    CustomCheckBox(neuAttrs = defaultFlatNeuAttrs())
                    CustomRadioButton(neuAttrs = defaultPressedNeuAttrs())
                    CustomRadioButton(neuAttrs = defaultFlatNeuAttrs())
                }
                CustomLinearProgressIndicatorWithSlider()
                CustomButton()
                Row (modifier = Modifier.padding(16.dp)){
                   CustomPressedIconButton(filledVector = Icons.Filled.EmojiEvents, outlinedVector = Icons.Outlined.EmojiEvents)
                   CustomPressedIconButton(filledVector = Icons.Filled.ThumbUp, outlinedVector = Icons.Outlined.ThumbUp)
                   CustomFlatIconButton(filledVector = Icons.Default.EmojiEmotions, outlinedVector = Icons.Outlined.EmojiEmotions)
                   CustomFlatIconButton(filledVector = Icons.Default.Anchor, outlinedVector = Icons.Outlined.Anchor)

                }
                Row(
                    modifier = Modifier.padding(16.dp)
                ){
                    CustomCircularProgressIndicator()
                    CustomContainedLoadingIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CustomContainedLoadingIndicator(){
    var isPurple200 by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        while (true){
            delay(200)
            isPurple200=!isPurple200
        }
    }
    val color by animateColorAsState(
        targetValue = if(isPurple200) Purple200 else Purple500,
        animationSpec = tween(durationMillis = 200)
    )
    ContainedLoadingIndicator(
        modifier = Modifier
            .padding(16.dp)
            .size(64.dp)
            .neu(
                defaultPressedNeuAttrs(cornerRadius = 36.dp)
            ),
        indicatorColor = color,
        containerColor = AppTheme.customColors.background,
        containerShape = CircleShape,
        polygons = LoadingIndicatorDefaults.IndeterminateIndicatorPolygons
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CustomCircularProgressIndicator(){
    CircularWavyProgressIndicator(
        color = Purple500,
        modifier = Modifier
            .padding(16.dp)
            .size(64.dp)
            .neu(
                defaultPressedNeuAttrs(cornerRadius = 36.dp)

            )
            .padding(4.dp)
        ,
        trackColor = Purple200,
        stroke = WavyProgressIndicatorDefaults.circularTrackStroke,
        trackStroke = WavyProgressIndicatorDefaults.circularIndicatorStroke
    )
}

@Composable
fun CustomFlatIconButton(filledVector: ImageVector,outlinedVector: ImageVector){
    var isPressed by remember { mutableStateOf(false) }
    IconButton(
        onClick = {
            isPressed=!isPressed
        },
        modifier = Modifier
            .padding(16.dp)
            .neu(
                neuAttrs=if(isPressed) defaultPressedNeuAttrs(24.dp) else defaultFlatNeuAttrs(24.dp)
            )
            .background(color = AppTheme.customColors.background, shape = CircleShape)
    ) {
        Icon(
            imageVector = if(isPressed) filledVector else outlinedVector,
            contentDescription = "icon button",
            tint = if(isPressed) Purple500 else Purple200
        )
    }
}

@Composable
fun CustomPressedIconButton(filledVector: ImageVector,outlinedVector: ImageVector){
    var isPressed by remember { mutableStateOf(false) }
    IconButton(
        onClick = {
            isPressed=!isPressed
        },
        modifier = Modifier
            .padding(16.dp)
            .neu(
            neuAttrs=if(!isPressed) defaultPressedNeuAttrs(cornerRadius = 24.dp) else defaultFlatNeuAttrs(24.dp)
            )
            .background(color = AppTheme.customColors.background, shape = CircleShape)
    ) {
        Icon(
           imageVector = if(isPressed) filledVector else outlinedVector,
           contentDescription = "icon button",
           tint = if(isPressed) Purple500 else Purple200
        )
    }
}

@Composable
fun CustomButton(){
    var isPressed by remember { mutableStateOf(false) }
    Button(
        onClick = {
            isPressed=!isPressed
        },
        modifier = Modifier
            .padding(top = 26.dp)
            .requiredWidth(300.dp)
            .neu(
                neuAttrs = if(isPressed) defaultPressedNeuAttrs() else defaultFlatNeuAttrs()
            )
            .background(color = AppTheme.customColors.background, shape = RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.customColors.background
        )
    ) {
        Text(
            text = "Button",
            color = AppTheme.customColors.textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomLinearProgressIndicatorWithSlider(){
    var progress by remember { mutableFloatStateOf(0.1f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = WavyProgressIndicatorDefaults.ProgressAnimationSpec
    )
    LinearWavyProgressIndicator(
        progress={
            animatedProgress
        },
        color = Purple500,
        trackColor = Purple200,
        modifier = Modifier
            .padding(24.dp)
            .neu(
            neuAttrs = defaultFlatNeuAttrs()
        ),

    )
    Spacer(
        modifier = Modifier.requiredHeight(20.dp)
    )
    Slider(
        value = progress,
        onValueChange = {
            progress=it
        },
        modifier = Modifier
            .neu(
                defaultPressedNeuAttrs(elevation = 6.dp)
            )
            .requiredWidth(300.dp),
        thumb = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp)
                    .background(Purple200, CutCornerShape(20.dp))

            )
        },
        track = {sliderState ->
            val fraction by remember {
                derivedStateOf {
                    (sliderState.value-sliderState.valueRange.start)/(sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction)
                        .align(alignment = Alignment.CenterStart)
                        .height(6.dp)
                        .padding(end = 16.dp)
                        .background(color = Purple500, shape = CircleShape)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f-fraction)
                        .align(alignment = Alignment.CenterEnd)
                        .height(1.dp)
                        .padding(start = 16.dp)
                        .background(color = AppTheme.customColors.trackColor, shape = CircleShape)
                )
            }
        }



    )

}

@Composable
fun CustomRadioButton(neuAttrs: NeuAttrs){
    var isSelected by remember { mutableStateOf(false) }
    RadioButton(
        selected = isSelected,
        onClick = {
            isSelected=!isSelected
        },
        modifier = Modifier
            .padding(16.dp)
            .neu(neuAttrs)
            .background(AppTheme.customColors.background,RoundedCornerShape(12.dp)),
        colors = RadioButtonDefaults.colors(
            selectedColor = Purple500,
            unselectedColor = Purple200
        )
    )
}
@Composable
fun CustomCheckBox(neuAttrs: NeuAttrs){
    val focusManager= LocalFocusManager.current
    var isChecked by remember { mutableStateOf(false) }
    Checkbox(
        checked = isChecked,
        onCheckedChange = {
            isChecked=it
            focusManager.clearFocus()

        },
        modifier = Modifier
            .padding(16.dp)
            .neu(neuAttrs=neuAttrs)
            .background(AppTheme.customColors.background,RoundedCornerShape(12.dp)),
        colors = CheckboxDefaults.colors(
            checkedColor = Purple500,
            uncheckedColor = Purple200,
            checkmarkColor = AppTheme.customColors.textColor
        )
    )
}
@Composable
fun CustomTextFields(){
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    val interactionSource1 = remember { MutableInteractionSource() }
    val interactionSource2 = remember { MutableInteractionSource() }

    val lightSource1 = rememberLightSource(interactionSource1)
    val lightSource2 = rememberLightSource(interactionSource2)

    TextField(
            value = username,
            onValueChange = {
                username = it
            },
            modifier = Modifier
                .padding(16.dp)
                .neu(
                    neuAttrs = if (lightSource1.value == LightSource.LEFT_BOTTOM) defaultFlatNeuAttrs() else defaultPressedNeuAttrs()
                ),
            placeholder = {
                Text(
                    text = "Enter Username"
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            colors = textFiledColors(),
            shape = RoundedCornerShape(16.dp),
            interactionSource = interactionSource1
        )
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier
                .padding(16.dp)
                .neu(neuAttrs = if (lightSource2.value == LightSource.LEFT_BOTTOM) defaultFlatNeuAttrs() else defaultPressedNeuAttrs()),
            placeholder = {
                Text(
                    text = "Enter Password"
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            colors = textFiledColors(),
            shape = RoundedCornerShape(16.dp),
            visualTransformation = PasswordVisualTransformation(),
            interactionSource = interactionSource2
        )



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopbar(isDarkTheme: Boolean,onToggleTheme:(Boolean)-> Unit){
    TopAppBar(
        title = {
            Text(
                text = "Neumorphic UI",
                color = AppTheme.customColors.textColor,
                modifier = Modifier.padding(16.dp)
            )
        },
        actions = {
            IconToggleButton(
                checked = isDarkTheme,
                onCheckedChange = {
                    onToggleTheme(it)
                },
                modifier = Modifier.padding(16.dp).size(48.dp).neu(defaultFlatNeuAttrs(24.dp)).background(
                    AppTheme.customColors.background,CircleShape),
                colors = IconButtonDefaults.iconToggleButtonColors(
                    containerColor = AppTheme.customColors.background
                )
            ) {
                Icon(
                    imageVector = if(isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                    contentDescription = "Theme toggle icon",
                    tint = Purple200
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.customColors.background
        )
    )
}

@Composable
private fun defaultFlatNeuAttrs(cornerRadius: Dp =12.dp,elevation: Dp=6.dp): NeuAttrs{
    return NeuAttrs(
        lightShadowColor = AppTheme.customColors.lightShadow,
        darkShadowColor = AppTheme.customColors.darkShadow,
        shadowElevation = elevation,
        shape = Flat(RoundedCorner(cornerRadius)),
        lightSource = LightSource.LEFT_TOP
    )
}
@Composable
private fun defaultPressedNeuAttrs(cornerRadius: Dp=12.dp,elevation: Dp=6.dp): NeuAttrs{
    return NeuAttrs(
        lightShadowColor = AppTheme.customColors.lightShadow,
        darkShadowColor = AppTheme.customColors.darkShadow,
        shadowElevation = elevation,
        shape = Pressed(RoundedCorner(cornerRadius)),
        lightSource = LightSource.LEFT_TOP
    )
}
@Composable
fun rememberLightSource(
    interactionSource: InteractionSource
): State<LightSource> {
    val lightSource = remember { mutableStateOf(LightSource.LEFT_BOTTOM) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            lightSource.value = when (interaction) {
                is FocusInteraction.Focus -> LightSource.LEFT_TOP
                is FocusInteraction.Unfocus -> LightSource.LEFT_BOTTOM
                else -> lightSource.value
            }
        }
    }
    return lightSource
}
@Composable
fun textFiledColors(): TextFieldColors{
    return TextFieldDefaults.colors(
        focusedContainerColor = AppTheme.customColors.background,
        unfocusedContainerColor = AppTheme.customColors.background,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        focusedLeadingIconColor = Purple200,
        unfocusedLeadingIconColor = Purple200,
        unfocusedTextColor = AppTheme.customColors.textColor,
        focusedTextColor = AppTheme.customColors.textColor,
        focusedPlaceholderColor = AppTheme.customColors.placeholderTextColor,
        unfocusedPlaceholderColor = AppTheme.customColors.placeholderTextColor,
    )
}
@Preview(device = "spec:width=411dp,height=891dp,dpi=420")
@Composable
fun MainScreenPreview(){
    MainScreen()
}