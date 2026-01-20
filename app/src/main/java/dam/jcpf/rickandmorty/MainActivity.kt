package dam.jcpf.rickandmorty

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dam.jcpf.rickandmorty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var user: FirebaseUser? = null

    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            showMain()
        }else{
            showLogin()
        }
    }

    private fun showMain() {
        setContentView(binding.root)
        binding.logoutbtn.setOnClickListener {
            signOut()
        }
    }

    private fun signOut(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show()
                showLogin()
            }
    }
    private fun showLogin() {
        user = null
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.rickandmorty)
            .setTheme(R.style.MiTema)
            .build()
        signInLauncher.launch(signInIntent)
    }
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            user = FirebaseAuth.getInstance().currentUser
            showMain()

        } else {
            Toast.makeText(this, "Error sign in", Toast.LENGTH_SHORT).show()
        }
    }



}