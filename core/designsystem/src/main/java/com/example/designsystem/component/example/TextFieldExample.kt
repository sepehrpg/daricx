package com.example.designsystem.component.example
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.component.AppIcon


//Example
//References::https://medium.com/@ramadan123sayed/comprehensive-guide-to-textfields-in-jetpack-compose-f009c4868c54#:~:text=The%20Basic%20TextField%20is%20the,and%20basic%20styling%20using%20modifiers.&text=Detailed%20Explanation%3A,UI%20in%20a%20declarative%20manner.
//..................................................................................................
@Composable
fun OutlinedTextFieldExample() {
    val text = remember { mutableStateOf("") }
    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text("Email Address") },
        placeholder = { Text("example@domain.com") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        isError = text.value.isNotEmpty() && !text.value.contains('@'),
    )
}
@Composable
fun TextFieldWithIconsExample() {
    val text = remember { mutableStateOf("") }
    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text("Search") },
        placeholder = { Text("Type here...") },
        singleLine = true,
        leadingIcon = { AppIcon(Icons.Filled.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (text.value.isNotEmpty()) {
                IconButton(onClick = { text.value = "" }) {
                    AppIcon(Icons.Filled.Clear, contentDescription = "Clear Text")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
    )
}
@Composable
fun PasswordTextFieldExample() {
    var password = remember { mutableStateOf("") }
    TextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        isError = password.value.isNotEmpty() && password.value.length < 8,
        colors = TextFieldDefaults.colors()
    )
}
@Composable
fun TextFieldWithErrorExample() {
    var text = remember { mutableStateOf("") }
    val isValid = text.value.length >= 3
    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text("Username") },
        isError = !isValid,
        placeholder = {
            if (!isValid) Text("Username must be at least 3 characters")
        },
        modifier = Modifier
            .fillMaxWidth(),
    )
}
@Composable
fun SearchBarTextFieldExample() {
    var query = remember { mutableStateOf("") }
    TextField(
        value = query.value,
        onValueChange = { query.value = it },
        placeholder = { Text("Search...") },
        singleLine = true,
        leadingIcon = { AppIcon(Icons.Filled.Search, contentDescription = "Search Icon") },
        trailingIcon = {
            if (query.value.isNotEmpty()) {
                IconButton(onClick = { query.value = "" }) {
                    AppIcon(Icons.Filled.Clear, contentDescription = "Clear Text")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}
@Composable
fun PasswordTextFieldWithToggleExample() {
    val (password, setPassword) = remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = setPassword,
        label = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Lock else Icons.Filled.CheckCircle
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { setPasswordVisible(!passwordVisible) }) {
                AppIcon(image, contentDescription = description)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun EnhancedTextFieldExample() {
    val text = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text("Label") },
        placeholder = { Text("Enter text") },
        leadingIcon = { AppIcon(Icons.Filled.Info, contentDescription = "Info") },
        trailingIcon = {
            IconButton(onClick = { text.value = "" }) {
                AppIcon(Icons.Filled.Clear, contentDescription = "Clear")
            }
        },
        isError = text.value.length > 10,
        visualTransformation = if (text.value.length > 10) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        singleLine = true,
        maxLines = 1,
        readOnly = false,
        enabled = true,
        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Blue,
            unfocusedTextColor = Color.Gray,
            disabledTextColor = Color.LightGray,
            errorTextColor = Color.Red,
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            disabledContainerColor = Color.Gray,
            errorContainerColor = Color.Red,
            cursorColor = Color.Blue,
            errorCursorColor = Color.Red,
            selectionColors = TextSelectionColors(
                handleColor = Color.Blue,
                backgroundColor = Color.LightGray.copy(alpha = 0.4f)
            ),
            focusedIndicatorColor = Color.Green,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.LightGray,
            errorIndicatorColor = Color.Yellow,
            focusedLeadingIconColor = Color.Magenta,
            unfocusedLeadingIconColor = Color.Cyan,
            disabledLeadingIconColor = Color.Gray,
            errorLeadingIconColor = Color.Gray,
            focusedTrailingIconColor = Color.Magenta,
            unfocusedTrailingIconColor = Color.Cyan,
            disabledTrailingIconColor = Color.Gray,
            errorTrailingIconColor = Color.Red,
            focusedLabelColor = Color.Magenta,
            unfocusedLabelColor = Color.Gray,
            disabledLabelColor = Color.LightGray,
            errorLabelColor = Color.Red,
            focusedPlaceholderColor = Color.Magenta,
            unfocusedPlaceholderColor = Color.LightGray,
            disabledPlaceholderColor = Color.Gray,
            errorPlaceholderColor = Color.Red,
            focusedSupportingTextColor = Color.Green,
            unfocusedSupportingTextColor = Color.Gray,
            disabledSupportingTextColor = Color.LightGray,
            errorSupportingTextColor = Color.Red,
            focusedPrefixColor = Color.Blue,
            unfocusedPrefixColor = Color.Gray,
            disabledPrefixColor = Color.LightGray,
            errorPrefixColor = Color.Red,
            focusedSuffixColor = Color.Blue,
            unfocusedSuffixColor = Color.Gray,
            disabledSuffixColor = Color.LightGray,
            errorSuffixColor = Color.Red
        )
    )
}
@Composable
fun NumberInputFieldExample() {
    var number by remember { mutableStateOf("") }
    TextField(
        value = number,
        onValueChange = { input ->
            // Filter to allow only digits
            if (input.all { it.isDigit() }) {
                number = input
            }
        },
        label = { Text("Enter Number") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}
@Composable
fun PhoneNumberInputFieldExample() {
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier) {
        TextField(
            value = phoneNumber,
            onValueChange = { input ->
                if (input.all { it.isDigit() } && input.length <= 10) { // Adjust length as per validation needs
                    phoneNumber = input
                    errorMessage = null
                } else {
                    errorMessage = "Please enter a valid phone number."
                }
            },

            label = { Text("Enter Phone Number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
@Composable
fun EmailInputFieldExample() {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Regular expression to validate email format
    val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
    Column(modifier = Modifier) {
        TextField(
            value = email,
            onValueChange = { input ->
                email = input
                errorMessage = if (input.matches(emailPattern.toRegex())) {
                    null
                } else {
                    "Please enter a valid email address."
                }
            },
            label = { Text("Enter Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
@Composable
fun MultiLineTextFieldExample() {
    var text by remember { mutableStateOf("") }
    val charLimit = 150
    TextField(
        value = text,
        onValueChange = {
            if (it.length <= charLimit) {
                text = it
            }
        },
        label = { Text("Enter your comment") },
        placeholder = { Text("Write something...") },
        modifier = Modifier
            .fillMaxWidth(),
        maxLines = 5, // Allows for multiple lines
        textStyle = TextStyle(color = Color.Black),
        colors = TextFieldDefaults.colors(),
        singleLine = false
    )
}
@Composable
fun MaskedInputFieldExample() {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            // Extract digits and format the text while keeping the cursor at the end
            val digits = newValue.text.replace(Regex("[^\\d]"), "")
            val formattedText = when {
                digits.length >= 10 -> "(${digits.substring(0, 3)}) ${
                    digits.substring(
                        3,
                        6
                    )
                }-${digits.substring(6, 10)}"

                digits.length >= 6 -> "(${digits.substring(0, 3)}) ${
                    digits.substring(
                        3,
                        6
                    )
                }-${digits.substring(6)}"

                digits.length >= 3 -> "(${digits.substring(0, 3)}) ${digits.substring(3)}"
                else -> digits
            }

            // Update the TextFieldValue with the formatted text and position the cursor at the end
            textFieldValue = TextFieldValue(
                text = formattedText,
                selection = TextRange(formattedText.length) // Cursor at the end
            )
        },
        label = { Text("Enter Phone Number") },
        placeholder = { Text("(123) 456-7890") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
    )
}
@Composable
fun CapitalizedTextFieldExample() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { input ->
            text = input.replace(Regex("^(\\w)|\\s+(\\w)")) {
                it.value.uppercase()
            }
        },
        label = { Text("Full Name") },
        placeholder = { Text("John Doe") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}
@Composable
fun PrefixSuffixTextFieldExample() {
    var amount by remember { mutableStateOf("") }

    TextField(
        value = amount,
        onValueChange = { input ->
            // Allow only numbers and decimal points
            if (input.all { it.isDigit() || it == '.' }) {
                amount = input
            }
        },
        label = { Text("Amount") },
        placeholder = { Text("0.00") },
        leadingIcon = { Text("$") }, // Prefix for currency
        trailingIcon = { Text("USD") }, // Suffix for currency code
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
    )
}
@Composable
fun EnhancedCapsuleTextFieldExample() {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }  // Set this based on validation logic
    var isSuccess by remember { mutableStateOf(false) }  // Set this based on validation logic

    // Dynamic border color based on state (error, success, focus)
    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> Color.Red
            isSuccess -> Color(0xFF4CAF50) // Green color for success
            isFocused -> Color.Black
            else -> Color.Gray
        }
    )

    // Dynamic text color
    val textColor by animateColorAsState(
        targetValue = if (isError) Color.Red else Color.Black
    )

    // Wrapping container with padding, shadow, and background gradient
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        // .padding(16.dp)
        //  .zIndex(1f), // Ensures it's above other content
        contentAlignment = Alignment.TopStart
    ) {
        // Card container for the capsule TextField
        Card(
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(12.dp), // Enhanced shadow
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F5F5) // Light background
            ),
            modifier = Modifier
                .fillMaxWidth()
            //  .padding(8.dp)
        ) {

            // Capsule-shaped BasicTextField with enhancements
            BasicTextField(
                value = textState,
                onValueChange = {
                    textState = it
                    // Add logic for error or success validation
                    isError = textState.text.length < 3 // Example validation for error
                    isSuccess = textState.text.length >= 3 // Example validation for success
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFFBBDEFB), Color(0xFFE3F2FD))
                        ),
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = borderColor,
                        shape = CircleShape
                    )
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ), // Internal padding for a better look
                cursorBrush = SolidColor(if (isError) Color.Red else Color.Blue),
                textStyle = TextStyle(
                    color = textColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,  // Align text to the start (left)
                    shadow = Shadow(color = Color.Gray, blurRadius = 1f) // Subtle shadow for text
                ),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (textState.text.isEmpty()) {
                            Text(
                                text = "Enter your text here...",
                                color = Color.Gray,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Start // Placeholder text aligned to the start
                            )
                        }
                        innerTextField() // The actual text field
                    }
                }
            )
        }

    }
}
//..................................................................................................




@Preview(showBackground = true, showSystemUi = true, locale = "en")
@Composable
private fun TextFieldExamplePreview() {
    LazyColumn(Modifier.fillMaxSize()) {
        item {  Box(Modifier.padding(vertical = 20.dp, horizontal = 20.dp)){OutlinedTextFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){TextFieldWithIconsExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){TextFieldWithErrorExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){PasswordTextFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){SearchBarTextFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){PasswordTextFieldWithToggleExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){EnhancedTextFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){NumberInputFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){PhoneNumberInputFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){EmailInputFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){MultiLineTextFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){MaskedInputFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){CapitalizedTextFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){PrefixSuffixTextFieldExample()} }
        item {  Box(Modifier.padding(vertical = 5.dp, horizontal = 20.dp)){EnhancedCapsuleTextFieldExample()} }
    }
}