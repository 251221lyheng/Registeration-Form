package com.example.registerform

import android.os.Build
import android.os.Bundle
import android.service.autofill.UserData
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.registerform.ui.theme.RegisterFormTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterFormTheme {
               Registerform()
            }
        }
    }
}


data class UserData(
    var userName: String = "",
    var gender: String = "",
    var phoneNumber : String = "",
    var address : String = ""
)

// definded viewModel class
class ResgisterVm : ViewModel() {
    var userData = mutableStateOf(UserData())
    fun updateUserName(input: String) {
        userData.value = userData.value.copy(
            userName = input
        )
    }

    fun updateGender(input:String){
        userData.value = userData.value.copy(
            gender = input
        )
    }
    fun updatePhoneNumber(input:String){
        userData.value = userData.value.copy(
            phoneNumber = input
        )
    }
    fun updateAddress(input:String){
        userData.value = userData.value.copy(
            address = input
        )
    }
    fun isFormValid():Boolean{
        return userData.value.userName.isNotBlank()&&
                userData.value.gender.isNotBlank()&&
                userData.value.phoneNumber.isNotBlank()&&
                userData.value.address.isNotBlank()
    }
}

@Composable
fun Registerform() {
    var viewModel: ResgisterVm = viewModel()
    val isFormValid by remember {
        derivedStateOf { viewModel.isFormValid() }
    }
    Box(){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),

            ) {
            Row(
                Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Registration Form",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "account" )
            }
            TextFieldComponent("Enter your name", "Full Name", { viewModel.updateUserName(it) }, "username", viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                TextFieldComponent("Gender", "Gender", { viewModel.updateGender(it) }, "gender",vm = viewModel,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(top = 4.dp))
                Spacer(modifier = Modifier.padding(5.dp))
                TextFieldComponent(placeholder = "Phone Number", label = "Phone" ,onTextChange = {viewModel.updatePhoneNumber(it)} , event = "phone" , vm = viewModel,
                    modifier = Modifier.padding(top = 4.dp),
                    keyboardType = KeyboardType.Number
                )
            }

            TextFieldComponent(placeholder = "Address", label = "Address", onTextChange = {viewModel.updateAddress(it)} , event = "address" , vm = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            Button(onClick = {},
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Text(text = "Register")
            }
        }
    }

}

@Composable
fun TextFieldComponent(
    placeholder: String,
    label: String,
    onTextChange: (text: String) -> Unit,
    event: String,
    vm: ResgisterVm,
    modifier: Modifier=Modifier,
    keyboardType: KeyboardType = KeyboardType.Text

) {
    OutlinedTextField(

        //  value = if (event == "username") vm.userData.value.name else vm.userData.value.career,
        value = when (event) {
            "username" -> vm.userData.value.userName
            "gender" -> vm.userData.value.gender
            "phone" -> vm.userData.value.phoneNumber
            else -> vm.userData.value.address

        },

        onValueChange = {
            onTextChange(it)
        },
        placeholder = {
            Text(text = placeholder, fontSize = 16.sp)
        },
        label = { Text(text = label) },
        maxLines = 2,

        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ) ,
        modifier = modifier
    )

}