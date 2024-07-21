package com.perrankana.marketup.android.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perrankana.marketup.android.MyApplicationTheme

@Composable
fun ProfileScene() {
    ProfileView()
}

@Composable
fun ProfileView() {
    BackgroundCard {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Profile"
        )
    }
}

@Preview
@Composable
fun ProfilePreview() {
    MyApplicationTheme {
        ProfileView()
    }
}