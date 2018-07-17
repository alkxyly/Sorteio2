package alkxyly.com.br.sorteio

import alkxyly.com.br.sorteio.activity.SorteioActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {
    val TAG = "CreateAccount"
    //Init views
    lateinit var googleSignInButton: SignInButton
   // lateinit var facebookSignInButton: LoginButton
   //lateinit var twitterSignInButton: TwitterLoginButton
    //Request codes
    val GOOGLE_LOG_IN_RC = 1
   // val FACEBOOK_LOG_IN_RC = 2
   // val TWITTER_LOG_IN_RC = 3
    // Google API Client object.
    var googleApiClient: GoogleApiClient? = null
    // Firebase Auth Object.
    var firebaseAuth: FirebaseAuth? = null
    var currentUser : FirebaseUser? = null
    var conectado: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        googleSignInButton = findViewById<View>(R.id.google_sign_in_button) as SignInButton
        googleSignInButton.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                when (v?.id) {
                    R.id.google_sign_in_button -> {
                        Log.i(TAG, "Trying Google LogIn.")
                        googleLogin()
                    }

                }
            }
        })

        firebaseAuth = FirebaseAuth.getInstance()
        // Configure Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.request_client_id))
                .requestEmail()
                .build()
        // Creating and Configuring Google Api Client.
        googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this  /* OnConnectionFailedListener */) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()

    }
    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        this.currentUser = firebaseAuth!!.currentUser
        if (currentUser != null) {
            Log.i("TAG", currentUser!!.email.toString())
            this.conectado = true
        }
        Log.i(TAG,conectado.toString())
        if(conectado){
           startActivity(Intent(this, SorteioActivity::class.java))
        }
    }

    private fun googleLogin() {
        Log.i(TAG, "Starting Google LogIn Flow.")
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, GOOGLE_LOG_IN_RC)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(TAG, "Got Result code ${requestCode}.")
        if (requestCode == GOOGLE_LOG_IN_RC) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.i(TAG, "With Google LogIn, is result a success? ${result.isSuccess}.")
            if (result.isSuccess) {
                firebaseAuthWithGoogle(result.signInAccount!!)
            } else {
                Toast.makeText(this, "Some error occurred.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.i(TAG, "Authenticating user with firebase.")
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        firebaseAuth?.signInWithCredential(credential)?.addOnCompleteListener(this) { task ->
            Log.i(TAG, "Firebase Authentication, is result a success? ${task.isSuccessful}.")
            if (task.isSuccessful) {
                startActivity(Intent(this, SorteioActivity::class.java))

            } else {
                Log.e(TAG, "Authenticating with Google credentials in firebase FAILED !!")
            }
        }
    }
}
