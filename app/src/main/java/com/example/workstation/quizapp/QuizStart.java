package com.example.workstation.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * QuizStart Class Implements application:
 * to display different Quiz Question
 * and accept input option from user
 * check for correct options
 * and display Result
 *
 * @author Akshay Chhajed
 * @since  2018-03-19
 */

public class QuizStart extends AppCompatActivity {

    //A Boolean Array to store the question is displayed or not
    //if que no. n is displayed then arrq[n-1] will set to true;
    boolean[] arrq;
    //counter for counting no. of questions displayed
    //score will store score of Quiz
    int counter,score=0;
    //Constructor:
    //arrq[] is set initially to false i.e no question is displayed yet
    public QuizStart()
    {
        counter=0;
        arrq=new boolean[]{false,false,false,false,false,false,false,false,false,false,false,false};
    }

    /*question Class
      It holds question,options,question type,correct Answer
      Q->holds Question String
      options->Options to the Question(If There)
      qtype->if 1 then single option correct question
             if 2 then multile option correct question
             if 3 then EditText type question.
       ca->correct answer for qtype=1 and qtype=3.
       CA->array to hold correct Answer for qtype=2.
     */
    class question
    {
        public String Q;
        public String[] options;
        public int qtype;
        public String ca;
        public String[] CA;

        public question()
        {
            options=new String[4];
            CA=new String[4];
        }

    }

    /*questionDB class
      It implements all the application for Quiz app as well as stores Questions
     */

    class questionDB
    {
        //que is an array for holding question object
        public question[] que;

        //questionDB public Constructor which initialize que array with questions
        public questionDB()
        {
            que=new question[12];
            que[0]=new question();
            que[0].Q="Who dicovered Electricity......";
            que[0].options=new String[]{"Racheal Stoone","Chekani Fidelis","","Micheal Faraday"};
            que[0].ca="Micheal Faraday";
            que[1]=new question();
            que[1].Q="Which of the following metal is paramagnetic..........?";
            que[1].options=new String[]{"fe2+","mn2+","H+","CO2"};
            que[1].ca="mn2+";
            que[2]=new question();
            que[2].Q="The botanical name of mango is .......?";
            que[2].options=new String[]{"Mangnifera Indica","Manifera Indica","Magnifera Idica","Manefera Indica"};
            que[2].ca="Magnifera Indica";
            que[3]=new question();
            que[3].Q="Who proposed the laws of motion.......?";
            que[3].options=new String[]{"Isaac Newton","Isac Newton","Chekani fidelis","John Dalton"};
            que[3].ca="Isaac Newton";
            que[4]=new question();
            que[4].Q="Diffrenciate 2x/4x-5";
            que[4].options=new String[]{"-10/(4x-5)(4x-5)","10/(4x-5)(4x-5)","None of the above","4x-5/(4x-5)(4x-5)"};
            que[4].ca="-10/(4x-5)(4x-5)";
            que[5]=new question();
            que[5].Q="Octyl ethanoate produces ........smell?";
            que[5].options=new String[]{"Orange smell","Bannana smell","Pinapple smell","Mango smell"};
            que[5].ca="Orange smell";
            que[6]=new question();
            que[6].Q="Pentyl ethanoate produces ........smell??";
            que[6].options=new String[]{"Orange smell","Mango smell","Bannana smell","Pinapple smell"};
            que[6].ca="Bannana smell";
            que[7]=new question();
            que[7].Q="Diffrenciate (2x-3)(2x-3)?";
            que[7].options=new String[]{"10x+6","4x-6","6x-4","4x+6"};
            que[7].ca="4x+6";
            que[8]=new question();
            que[8].Q="The instrument used to measure the amount of a patients blood is called ........?";
            que[8].options=new String[]{"sphygmomanometer","bloodohumometer","heamo-humnometer","none of the above"};
            que[8].ca="sphygmomanometer";
            que[9]=new question();
            que[9].Q="Where is Kogi state university located........?";
            que[9].options=new String[]{"Anyigba ,Kogi state","Ayingba Kogi state","Lokoja, Kogi state","Ankpa, Kogi state"};
            que[9].ca="Anyigba ,Kogi state";
            que[10]=new question();
            que[10].Q="which of these are terminologies used in Genetics...?";
            que[10].options=new String[]{"Genes","Gametes","Zygote","None of the above"};
            que[10].qtype=2;
            que[10].CA=new String[]{"Genes","Gametes","Zygote"};
            que[11]=new question();
            que[11].qtype=3;
            que[11].Q="The Basic Unit of Inheritance is ........?";
            que[11].ca="Gene";

            for(int i=0;i<10;i++)
            {
                que[i].qtype=1;      //qtype=1-->single option correct question
            }
        }
    }

    //method to store correct Answer for Multiple Answer Correct Questions
    private String correctAnswerForMCAQ(String[] s)
    {
        String S="";
        for(int i=0;i<s.length;i++)
        {
            S+="\n"+s[i];
        }
        return S;
    }

    //method for comparing Answer for Multiple Correct Answer Question
    private boolean CBcheck(String S,String[] s)
    {
        boolean c=true;
        int count=0,counts=0;
        for(int i=0;i<s.length;i++)
        {
            if(s[i]=="");
            else if(S.indexOf(s[i])==-1)
            {
                c=false;
                break;
            }
            else count++;
        }
        for(int i=0;i<S.length();i++)
        {
            if(S.charAt(i)=='\n')
                counts++;
        }
        if(count!=counts)c=false;
        return c;
    }

    //method which selects question randomly from array and performs display operation
    public void perform(View view)
    {
        /*counter keeps track of no. of question displayed
          when no. of question displayed is 10 then
          It displays the score as TextView
          as well as Toast Message
          creates Exit button clicking on it exits activity
         */
        if(counter==10)
        {
            TextView QV=(TextView) findViewById(R.id.TVQ);
            RelativeLayout Ro= (RelativeLayout) findViewById(R.id.RLI);
            Ro.setVisibility(View.INVISIBLE);
            TextView TV=(TextView) findViewById(R.id.TVFD);
            TV.setVisibility(View.VISIBLE);
            TV.setText("Test Completed!!!\nSCORE:"+score);
            Toast T=Toast.makeText(getApplicationContext(),"Score:"+score+"/10",1000);
            T.show();
            Button B=(Button) findViewById(R.id.BV1);
            B.setText("EXIT");
            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    System.exit(0);
                }
            });
        }

        //Displays the question
        else {

            Button B = (Button) findViewById(R.id.BV1);
            B.setText("SUBMIT");
            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check(v);
                }
            });

            questionDB O = new questionDB();

            //arr is array to keep track of options which is displayed so that option can be randomized every time
            boolean[] arr;
            arr = new boolean[]{false, false, false, false};
            //Random Number Generator
            Random rand = new Random();
            int rnum;
            //this loop is applied for generating rnum such that this rnum no. option is not yet displayed
            do {
                rnum = rand.nextInt(12);
            } while (arrq[rnum] && counter != 10);

            //Question View
            TextView QV = (TextView) findViewById(R.id.TVQ);
            //Answer View->initially made invisible
            TextView AV = (TextView) findViewById(R.id.TVA);
            AV.setVisibility(view.INVISIBLE);
            //defining option fields for single option correct question
            RadioGroup RG = (RadioGroup) findViewById(R.id.RG);
            RadioButton RBO1 = (RadioButton) findViewById(R.id.RBO1);
            RadioButton RBO2 = (RadioButton) findViewById(R.id.RBO2);
            RadioButton RBO3 = (RadioButton) findViewById(R.id.RBO3);
            RadioButton RBO4 = (RadioButton) findViewById(R.id.RBO4);
            //defining EditBox for EditText type Question
            LinearLayout LLEB = (LinearLayout) findViewById(R.id.LLEB);
            //defining option fields for Multiple Option correct Question
            LinearLayout LLCB = (LinearLayout) findViewById(R.id.LLCB);
            CheckBox CBO1 = (CheckBox) findViewById(R.id.CBO1);
            CheckBox CBO2 = (CheckBox) findViewById(R.id.CBO2);
            CheckBox CBO3 = (CheckBox) findViewById(R.id.CBO3);
            CheckBox CBO4 = (CheckBox) findViewById(R.id.CBO4);


            int index, count = 0;
            //setting the question on view
            QV.setText(O.que[rnum].Q);
            //checking the question type of question
            //qtype=1 single option correct question will be displayed
            if (O.que[rnum].qtype == 1) {
                RG.setVisibility(View.VISIBLE);
                LLCB.setVisibility(View.INVISIBLE);
                LLEB.setVisibility(View.INVISIBLE);
                while (!(arr[0] && arr[1] && arr[2] && arr[3])) {
                    index = rand.nextInt(4);
                    if (arr[index] == false && count == 0) {
                        arr[index] = true;
                        RBO1.setText(O.que[rnum].options[index]);
                        count++;
                    } else if (arr[index] == false && count == 1) {
                        arr[index] = true;
                        RBO2.setText(O.que[rnum].options[index]);
                        count++;
                    } else if (arr[index] == false && count == 2) {
                        arr[index] = true;
                        RBO3.setText(O.que[rnum].options[index]);
                        count++;
                    } else if (arr[index] == false && count == 3) {
                        arr[index] = true;
                        RBO4.setText(O.que[rnum].options[index]);
                        count++;
                    }
                }
                AV.setText(O.que[rnum].ca);
            }
            //qtype=2 means Multiple answer correct question
            else if (O.que[rnum].qtype == 2) {
                RG.setVisibility(View.INVISIBLE);
                LLCB.setVisibility(View.VISIBLE);
                LLEB.setVisibility(View.INVISIBLE);
                while (!(arr[0] && arr[1] && arr[2] && arr[3])) {
                    index = rand.nextInt(4);
                    if (arr[index] == false && count == 0) {
                        arr[index] = true;
                        CBO1.setText(O.que[rnum].options[index]);
                        count++;
                    } else if (arr[index] == false && count == 1) {
                        arr[index] = true;
                        CBO2.setText(O.que[rnum].options[index]);
                        count++;
                    } else if (arr[index] == false && count == 2) {
                        arr[index] = true;
                        CBO3.setText(O.que[rnum].options[index]);
                        count++;
                    } else if (arr[index] == false && count == 3) {
                        arr[index] = true;
                        CBO4.setText(O.que[rnum].options[index]);
                        count++;
                    }
                }
                AV.setText(correctAnswerForMCAQ(O.que[rnum].CA));
            }
            //qtype=3 Edit text question
            else if (O.que[rnum].qtype == 3) {
                RG.setVisibility(View.INVISIBLE);
                LLCB.setVisibility(View.INVISIBLE);
                LLEB.setVisibility(View.VISIBLE);
                AV.setText(O.que[rnum].ca);
            }
            arrq[rnum] = true;
            counter++;
        }
    }

    public void check(View view)
    {
        //selecting proper view from XML layout
        RadioGroup RG=(RadioGroup) findViewById(R.id.RG);
        LinearLayout LLCB=(LinearLayout) findViewById(R.id.LLCB);
        LinearLayout LLEB=(LinearLayout) findViewById(R.id.LLEB);
        RadioButton RBO1=(RadioButton) findViewById(R.id.RBO1);
        RadioButton RBO2=(RadioButton) findViewById(R.id.RBO2);
        RadioButton RBO3=(RadioButton) findViewById(R.id.RBO3);
        RadioButton RBO4=(RadioButton) findViewById(R.id.RBO4);
        CheckBox CBO1 = (CheckBox) findViewById(R.id.CBO1);
        CheckBox CBO2 = (CheckBox) findViewById(R.id.CBO2);
        CheckBox CBO3 = (CheckBox) findViewById(R.id.CBO3);
        CheckBox CBO4 = (CheckBox) findViewById(R.id.CBO4);

        if(RG.getVisibility()==View.VISIBLE &&  LLCB.getVisibility()==View.INVISIBLE && LLEB.getVisibility()==View.INVISIBLE) {
            if (RBO1.isChecked() || RBO2.isChecked() || RBO3.isChecked() || RBO4.isChecked()) {
                String s;
                TextView AV = (TextView) findViewById(R.id.TVA);
                TextView EV = (TextView) findViewById(R.id.TVE);
                EV.setVisibility(view.INVISIBLE);
                s = AV.getText().toString();
                if (RBO1.isChecked()) {
                    String o;
                    o = RBO1.getText().toString();
                    if (o.equals(s)) {
                        AV.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                        AV.setText("Correct!!!\n" + s);
                        score++;
                    } else {
                        AV.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
                        AV.setText("Wrong!!!\nCorrect Answer is:-" + s);
                    }
                    AV.setVisibility(view.VISIBLE);
                } else if (RBO2.isChecked()) {
                    String o;
                    o = RBO2.getText().toString();
                    if (o.equals(s)) {
                        AV.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                        AV.setText(" Correct!!!\n" + s);
                        score++;
                    } else {
                        AV.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
                        AV.setText(" Wrong!!!\nCorrect Answer is:-" + s);
                    }
                    AV.setVisibility(view.VISIBLE);
                } else if (RBO3.isChecked()) {
                    String o;
                    o = RBO3.getText().toString();
                    if (o.equals(s)) {
                        AV.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                        AV.setText(" Correct!!!\n" + s);
                        score++;
                    } else {
                        AV.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
                        AV.setText(" Wrong!!!\nCorrect Answer is:-" + s);
                    }
                    AV.setVisibility(view.VISIBLE);
                } else if (RBO4.isChecked()) {
                    String o;
                    o = RBO4.getText().toString();
                    if (o.equals(s)) {
                        AV.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                        AV.setText("Correct!!!\n" + s);
                        score++;
                    } else {
                        AV.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
                        AV.setText("Wrong!!!\nCorrect Answer is:-" + s);
                    }
                    AV.setVisibility(view.VISIBLE);
                }
                Button B = (Button) findViewById(R.id.BV1);
                B.setText("NEXT");
                B.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        perform(v);
                    }
                });
            } else {
                TextView EV = (TextView) findViewById(R.id.TVE);
                EV.setText("Please Select One of the Option\n");
                EV.setVisibility(view.VISIBLE);
            }
        }
        if(RG.getVisibility()==View.INVISIBLE &&  LLCB.getVisibility()==View.VISIBLE && LLEB.getVisibility()==View.INVISIBLE) {
            if (CBO1.isChecked() || CBO2.isChecked() || CBO3.isChecked() || CBO4.isChecked()) {
                String s;
                TextView AV = (TextView) findViewById(R.id.TVA);
                TextView EV = (TextView) findViewById(R.id.TVE);
                EV.setVisibility(view.INVISIBLE);
                s = AV.getText().toString();
                String[] o=new String[]{"","","",""};
                int count=0;
                if (CBO1.isChecked()) {

                    o[count]=CBO1.getText().toString();
                    count++;
                }  if (CBO2.isChecked()) {
                    o[count]=CBO2.getText().toString();
                    count++;
                }  if (CBO3.isChecked()) {
                    o[count]=CBO3.getText().toString();
                    count++;
                }  if (CBO4.isChecked()) {
                    o[count]=CBO4.getText().toString();
                    count++;
                }
                if (CBcheck(s,o)) {
                    AV.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                    AV.setText(" Correct!!!\n"+s);
                    score++;
                } else {
                    AV.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
                    AV.setText(" Wrong!!!\n Correct Answer is:-" + s);
                }
                AV.setVisibility(view.VISIBLE);
                Button B = (Button) findViewById(R.id.BV1);
                B.setText("NEXT");
                B.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        perform(v);
                    }
                });
            } else {
                TextView EV = (TextView) findViewById(R.id.TVE);
                EV.setText("Please Select One of the Option\n");
                EV.setVisibility(view.VISIBLE);
            }
        }
        if(RG.getVisibility()==View.INVISIBLE &&  LLCB.getVisibility()==View.INVISIBLE && LLEB.getVisibility()==View.VISIBLE) {
            EditText ET=(EditText) findViewById(R.id.ET);
            TextView AV=(TextView) findViewById(R.id.TVA) ;
            String s=ET.getText().toString().toLowerCase();
            String o=AV.getText().toString().toLowerCase();
            if (s.equals(o)) {
                AV.setTextColor(this.getResources().getColor(R.color.colorPrimary));
                AV.setText("Correct!!!\n"+AV.getText().toString());
                score++;
            } else {
                AV.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
                AV.setText(" Wrong!!!\nCorrect Answer is:-" +AV.getText().toString() );
            }
            AV.setVisibility(view.VISIBLE);
            Button B = (Button) findViewById(R.id.BV1);
            B.setText("NEXT");
            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    perform(v);
                }
            });

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_start);
        View V=new View(this);
        perform(V);
    }

}
