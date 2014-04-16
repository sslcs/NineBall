package com.game.weight;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMain extends Activity
{
    private int container = 1;

    private ArrayList<Integer> arrayLeft = new ArrayList<Integer>(4);
    private ArrayList<Integer> arrayRight = new ArrayList<Integer>(4);

    private TextView tvSpecial;
    private TextView[] tvsLeft;
    private TextView[] tvsRight;
    private TextView[] tvTimes;
    private View btnWeight;
    private View[] tvBalls;
    private int special, specialWeight, answer, answerWeight = -1;
    private int times = 0;
    private int inWhich = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random(System.currentTimeMillis());
        special = random.nextInt(8);
        specialWeight = random.nextInt(1);

        tvSpecial = (TextView) findViewById(R.id.tv_special_ball);

        tvBalls = new View[9];
        tvsLeft = new TextView[4];
        tvsRight = new TextView[4];
        tvTimes = new TextView[3];
        for (int i = 0; i < 9; i++)
        {
            final int index = i;
            tvBalls[i] = findViewById(R.id.tv_1 + i);
            tvBalls[i].setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onSelcted(index);
                }
            });
        }
        for (int i = 0; i < 4; i++)
        {
            tvsLeft[i] = (TextView) findViewById(R.id.tv_left_1 + i);
        }
        for (int i = 0; i < 4; i++)
        {
            tvsRight[i] = (TextView) findViewById(R.id.tv_right_1 + i);
        }
        for (int i = 0; i < 3; i++)
        {
            tvTimes[i] = (TextView) findViewById(R.id.tv_time_1 + i);
        }

        findViewById(R.id.ll_left).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                container = 1;
            }
        });

        findViewById(R.id.ll_right).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                container = 2;
            }
        });

        findViewById(R.id.tv_special_ball).setOnClickListener(
                new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        container = 3;
                    }
                });

        btnWeight = findViewById(R.id.btn_weight);
        btnWeight.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                weight();
            }
        });

        findViewById(R.id.btn_clear).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clear();
            }
        });

        findViewById(R.id.btn_finish).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                answer();
            }
        });

        findViewById(R.id.btn_again).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                init();
            }
        });
    }

    private void init()
    {
        Random random = new Random(System.currentTimeMillis());
        special = random.nextInt(8);
        specialWeight = random.nextInt(1);
        
        times = 0;
        inWhich = 0;
        container = 1;
        btnWeight.setEnabled(true);
        clear();
        tvBalls[answer].setVisibility(View.VISIBLE);
        tvSpecial.setText("");
        for (int i = 0; i < 3; i++)
        {
            tvTimes[i].setText("");
        }
    }

    private void weight()
    {
        if(arrayLeft.size() == 0 || arrayLeft.size() != arrayRight.size())
        {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("第" + (times + 1) + "次：");
        for (int i : arrayLeft)
        {
            sb.append(i + 1);
        }

        if (0 == inWhich)
        {
            sb.append("==");
        }
        else if (1 == inWhich)
        {
            if (0 == specialWeight)
            {
                sb.append(">");
            }
            else
            {
                sb.append("<");
            }
        }
        else
        {
            if (0 == specialWeight)
            {
                sb.append("<");
            }
            else
            {
                sb.append(">");
            }
        }

        for (int i : arrayRight)
        {
            sb.append(i + 1);
        }

        tvTimes[times].setText(sb.toString());

        if (++times > 2)
        {
            btnWeight.setEnabled(false);
        }
    }

    private void clear()
    {
        inWhich = 0;
        for (int i = 0; i < arrayLeft.size(); i++)
        {
            tvsLeft[i].setVisibility(View.GONE);
            tvBalls[arrayLeft.get(i)].setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < arrayRight.size(); i++)
        {
            tvsRight[i].setVisibility(View.GONE);
            tvBalls[arrayRight.get(i)].setVisibility(View.VISIBLE);
        }
        arrayLeft.clear();
        arrayRight.clear();
    }

    private void answer()
    {
        RadioGroup rg = (RadioGroup) findViewById(R.id.weight);
        if (R.id.radio_heavy == rg.getCheckedRadioButtonId())
        {
            answerWeight = 0;
        }
        else
        {
            answerWeight = 1;
        }

        if (answer == special && answerWeight == specialWeight)
        {
            Toast.makeText(this, "Bingo!", Toast.LENGTH_LONG).show();
        }
        else
        {
            StringBuffer sb = new StringBuffer("Lose: " + (special + 1) + "号球");
            sb.append((specialWeight == 0) ? "偏重" : "偏轻");
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void onSelcted(int index)
    {
        if (1 == container)
        {
            fillLeft(index);
        }
        else if (2 == container)
        {
            fillRight(index);
        }
        else
        {
            fillSpecial(index);
        }
    }

    private void fillLeft(int index)
    {
        if(arrayLeft.size()>=4)
        {
            return;
        }
        
        tvBalls[index].setVisibility(View.INVISIBLE);
        arrayLeft.add(index);
        if (index == special)
        {
            inWhich = 1;
        }
        int size = arrayLeft.size();
        for (int i = 0; i < size; i++)
        {
            tvsLeft[i].setText((arrayLeft.get(i) + 1) + "");
            tvsLeft[i].setVisibility(View.VISIBLE);
        }
    }

    private void fillRight(int index)
    {
        if(arrayRight.size()>=4)
        {
            return;
        }
        
        tvBalls[index].setVisibility(View.INVISIBLE);
        arrayRight.add(index);
        if (index == special)
        {
            inWhich = 2;
        }
        int size = arrayRight.size();
        for (int i = 0; i < size; i++)
        {
            tvsRight[i].setText((arrayRight.get(i) + 1) + "");
            tvsRight[i].setVisibility(View.VISIBLE);
        }
    }

    private void fillSpecial(int index)
    {
        answer = index;
        tvSpecial.setText((answer + 1) + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
