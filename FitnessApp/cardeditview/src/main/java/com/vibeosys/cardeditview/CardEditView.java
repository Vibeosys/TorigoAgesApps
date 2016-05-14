package com.vibeosys.cardeditview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.regex.Pattern;

/**
 * Created by akshay on 14-05-2016.
 */
public class CardEditView extends LinearLayout implements TextWatcher, View.OnFocusChangeListener {

    private ImageView mImgCard;
    private EditText mTxtCardNo;
    private Context mContext;
    private String mCardNo;
    private boolean mHasChanged = false;

    public CardEditView(Context context) {
        super(context);
    }

    public CardEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public CardEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        inflate(mContext, R.layout.card_edit_text, this);
        mImgCard = (ImageView) findViewById(R.id.cardType);
        mTxtCardNo = (EditText) findViewById(R.id.cardNumber);

        TypedArray array = mContext.obtainStyledAttributes(attributeSet, R.styleable.CardEditView);
        mTxtCardNo.setBackgroundColor(array.getColor(R.styleable.CardEditView_backColour, Color.WHITE));
        mTxtCardNo.setTextColor(array.getColor(R.styleable.CardEditView_textColour, Color.BLACK));

        mTxtCardNo.addTextChangedListener(this);
        mTxtCardNo.setOnFocusChangeListener(this);
    }

    public String getCardNo() {
        return mCardNo;
    }

    public void setCardNo(String mCardNo) {
        this.mCardNo = mCardNo;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (s.length() == 0) {
            mHasChanged = false;
            mImgCard.setImageResource(R.drawable.ic_credit_card_black_24dp);
        }
        if (s.length() >= 16) {
            CardType cardType = CardType.detect(s.toString().replace("-", ""));
            switch (cardType) {
                case VISA:
                    mImgCard.setImageResource(R.drawable.visa);
                    break;
                case MASTERCARD:
                    mImgCard.setImageResource(R.drawable.mastercard);
                    break;
                case AMERICAN_EXPRESS:
                    mImgCard.setImageResource(R.drawable.american_express);
                    break;
                case DINERS_CLUB:
                    mImgCard.setImageResource(R.drawable.diners_club);
                    break;
                case DISCOVER:
                    mImgCard.setImageResource(R.drawable.discover);
                    break;
                case JCB:
                    mImgCard.setImageResource(R.drawable.jcb);
                    break;
                default:
                    mImgCard.setImageResource(R.drawable.ic_credit_card_black_24dp);
                    break;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String strText = mTxtCardNo.getText().toString();
        int j = 0;
        if (strText.length() > 0 && !mHasChanged) {
            String strDisplayString = "";
            for (int i = 0; i < strText.length(); i++) {
                strDisplayString = strDisplayString + strText.charAt(i);
                j = j + 1;
                if (j == 4 && i != strText.length() - 1) {
                    j = 0;
                    strDisplayString = strDisplayString + "-";
                }

            }
            mTxtCardNo.setText(strDisplayString);
            mHasChanged = true;
        }
    }

    public enum CardType {

        UNKNOWN,
        VISA("^4[0-9]{12}(?:[0-9]{3})?$"),
        MASTERCARD("^5[1-5][0-9]{14}$"),
        AMERICAN_EXPRESS("^3[47][0-9]{13}$"),
        DINERS_CLUB("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"),
        DISCOVER("^6(?:011|5[0-9]{2})[0-9]{12}$"),
        JCB("^(?:2131|1800|35\\d{3})\\d{11}$");

        private Pattern pattern;

        CardType() {
            this.pattern = null;
        }

        CardType(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        public static CardType detect(String cardNumber) {

            for (CardType cardType : CardType.values()) {
                if (null == cardType.pattern) continue;
                if (cardType.pattern.matcher(cardNumber).matches()) return cardType;
            }

            return UNKNOWN;
        }

    }
}
