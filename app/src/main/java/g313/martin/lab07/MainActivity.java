package g313.martin.lab07;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_key;
    EditText et_value;

    DB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_key = findViewById(R.id.et_key);
        et_value = findViewById(R.id.et_value);

        mydb = new DB(this, "mybase.db", null, 1);
    }

    public void on_insert_click(View v)
    {
        String key = et_key.getText().toString();
        String value = et_value.getText().toString();
        try{
            mydb.do_insert(key, value);
            Toast.makeText(this, "Готово", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Ошибка! Возможно, запись с таким заголовком уже существует", Toast.LENGTH_SHORT).show();
        }
    }

    public void on_update_click(View v)
    {
        String key = et_key.getText().toString();
        String value = et_value.getText().toString();

        mydb.do_update(key, value);
        Toast.makeText(this, "Готово", Toast.LENGTH_SHORT).show();
    }

    public void on_select_click(View v)
    {
        String key = et_key.getText().toString();
        String value = mydb.do_select(key);

        et_value.setText(value);
    }

    public void on_delete_click(View v)
    {
        String key = et_key.getText().toString();

        if (mydb.do_select(key) == "(!) not found")
            Toast.makeText(this, "Ошибка! Возможно, запись с таким заголовком отсутствует", Toast.LENGTH_SHORT).show();
        else{
            AlertDialog.Builder bld = new AlertDialog.Builder(this);
            AlertDialog dlg = bld.create();
            dlg.setTitle("Удаление");
            dlg.setMessage("Вы уверены, что хотите удалить запись?");
            dlg.setButton(Dialog.BUTTON_POSITIVE,"Да", (dialog, id) -> mydb.do_delete(key));
            dlg.setButton(Dialog.BUTTON_NEGATIVE,"Нет", (dialog, id) -> dialog.cancel());
            dlg.show();
        }
    }
}