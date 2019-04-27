/* imports not shown */
public class QuizActivity extends Activity {
    /* Some fields and methods not shown */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("","onCreate");
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Log.d("", "onStart");
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        Log.d("", "onRestart");
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.d("", "onResume");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.d("", "onPause");
    }
    @Override
    public void onStop()
    {
        super.onStop();
        Log.d("", "onStop");
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("", "onDestroy");
    }
}