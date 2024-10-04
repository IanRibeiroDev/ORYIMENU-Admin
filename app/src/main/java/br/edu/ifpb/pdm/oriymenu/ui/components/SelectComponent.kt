package br.edu.ifpb.pdm.oriymenu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.edu.ifpb.pdm.oriymenu.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SelectComponent(
    elements: List<String>,
    isDropDownExpanded: StateFlow<Boolean>,
    onShowDropDown: () -> Unit,
    onCollapseDropDown: () -> Unit,
    currentElementIndex: StateFlow<Int>,
    onSelect: (Int) -> Unit,
) {
    val isExpanded by isDropDownExpanded.collectAsState()
    val currentIndex by currentElementIndex.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        onShowDropDown()
                    }
                    .padding(12.dp)
            ) {
                Text(text = elements[currentIndex])
                Image(
                    painter = painterResource(id = R.drawable.arrow_drop_down),
                    contentDescription = "Arrow Drop Down"
                )
            }
        }
        DropdownMenu(
            modifier = Modifier.padding(horizontal = 16.dp),
            expanded = isExpanded,
            onDismissRequest = {
                onCollapseDropDown()
            }
        ) {
            elements.forEachIndexed { index, name ->
                DropdownMenuItem(text = {
                    Text(text = name)
                },
                    onClick = {
                        onCollapseDropDown()
                        onSelect(index)
                    })
            }
        }
    }
}