package com.ebiggerr.sims.service.input;

import com.ebiggerr.sims.domain.request.itemWithImageRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class inputCheckValid {

    /**
     * <h1> Checks the format of email address </h1>
     * @param email email address
     * @return if the email address matches with the regex
     */
    public static boolean checkEmail(String email){

        final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern= Pattern.compile(EMAIL_REGEX);
        Matcher matcher= pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * <h1> Checks the format of price </h1>
     * @param price price of the item in form of x.xx
     * @return if the string given matches with the regex
     */
    public static boolean checkPriceDoublePrecision(String price){

        final String PRICE_REGEX="[0-9]+(\\.[0-9][0-9]?)?";
        Pattern pricePattern = Pattern.compile(PRICE_REGEX);
        Matcher priceMatcher = pricePattern.matcher(price);

        return priceMatcher.matches();
    }

    /**
     * <h1> Checks the format of volume or weight</h1>
     * @param VolumeWeight volume or weight if the item
     * @return if the data given matches with the regex
     */
    public static boolean checkVolumeWeightInput(String VolumeWeight){

        final String VOLUME_WEIGHT_REGEX="([0-9]+(\\.[0-9][0-9]?)?)\\s*(lbs?|kg|L)";
        Pattern volumeWeightPattern = Pattern.compile(VOLUME_WEIGHT_REGEX);
        Matcher volumeWeightMatcher = volumeWeightPattern.matcher(VolumeWeight);

        return volumeWeightMatcher.matches();

    }

    /**
     * <h1> Checks the format of dimensions of item</h1>
     * @param dimensions dimension of the item ( L x W x H )
     * @return if the String given matches with the regex
     */
    public static boolean checkDimension(String dimensions){

        final String DIMENSION_REGEX="(\\d*)-(\\d*)-(\\d*)";
        Pattern dimensionsPattern = Pattern.compile(DIMENSION_REGEX);
        Matcher dimensionMatcher = dimensionsPattern.matcher(dimensions);

        return dimensionMatcher.matches();
    }

    public static String checkAllForItem(itemWithImageRequest item){

        boolean valid = true;
        String message = null;

        if( !inputCheckValid.checkDimension( item.getDimensions() ) ) {
            valid = false;
            message = "Invalid Dimensions.(LxWxH) Valid Example: 3-2-3";
        }

        if( !inputCheckValid.checkPriceDoublePrecision( item.getUnitPrice() ) ) {
            valid = false;
            message = "Invalid Unit Price. Valid Example: 1.00";
        }

        if( !inputCheckValid.checkVolumeWeightInput( item.getVolume() ) ) {
            valid = false;
            message = "Invalid Volume/Weight. Valid Example: 1.00kg / 1L";
        }


        if(!valid){
            return message;
        }

        return null;

    }

    /**
     * <h1> Checks the format of the year</h1>
     * @param year year in String
     * @return if the String given matches with the regex
     */
    public static boolean checkYear(String year) {

        final String YEAR_REGEX = "^\\d{4}$";
        Pattern pattern= Pattern.compile(YEAR_REGEX);
        Matcher matcher= pattern.matcher(year);

        return matcher.matches();
    }
}
