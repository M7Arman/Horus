package com.arman.horus.providers;

import com.arman.horus.R;
import com.arman.horus.models.Address;
import com.arman.horus.models.CardItem;
import com.arman.horus.models.PlaceDetail;
import com.arman.horus.models.TripDetail;
import com.arman.horus.utils.AppStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arman on 4/28/16.
 */
public class ContentProvider {

    public static List<CardItem> popularTrips() {
        if (AppStatus.isOnline()) {
            // TODO: get from backend
        } else {
            //TODO: ????
        }
        throw new UnsupportedOperationException("the function is not implemented yet!");
    }

    public static List<CardItem> popularPlaces() {
        if (AppStatus.isOnline()) {
            // TODO: get from backend
        } else {
            //TODO: ????
        }
        throw new UnsupportedOperationException("the function is not implemented yet!");
    }

    public static List<CardItem> tripDetail(String id) {
        if (AppStatus.isOnline()) {
            // TODO: get from backend by ID
        } else {
            //TODO: ????
        }
        throw new UnsupportedOperationException("the function is not implemented yet!");
    }

    public static List<CardItem> placeDetail(String id) {
        if (AppStatus.isOnline()) {
            // TODO: get from backend by ID
        } else {
            //TODO: ????
        }
        throw new UnsupportedOperationException("the function is not implemented yet!");
    }

    public static List<CardItem> dummyTrips() {
        List<CardItem> cards = new ArrayList<>();
        cards.add(new CardItem("A title for card", R.drawable.trip, android.R.drawable.ic_lock_idle_lock, "trip_1"));
        cards.add(new CardItem("This item has too very-very long and ugly title", R.drawable.trip, android.R.drawable.stat_sys_speakerphone, "trip_2"));
        cards.add(new CardItem("Another long title for the third card", R.drawable.trip, android.R.drawable.btn_dialog, "trip_3"));
        return cards;
    }

    public static List<CardItem> dummyPlaces() {
        List<CardItem> cards = new ArrayList<>();
        cards.add(new CardItem("A title for card", R.drawable.trip, android.R.drawable.ic_lock_silent_mode_off, "place_1"));
        cards.add(new CardItem("This item has too very-very long and ugly title", R.drawable.trip, android.R.drawable.divider_horizontal_dim_dark, "place_2"));
        cards.add(new CardItem("Another long title for the third card", R.drawable.trip, android.R.drawable.btn_plus, "place_3"));
        return cards;
    }

    public static PlaceDetail dummyPlaceDetail() {
        String dummyDescription = "Հայերեն (ավանդական՝ հայերէն), հնդեվրոպական լեզվաընտանիքի առանձին ճյուղ հանդիսացող լեզու։ Հայաստանի և Լեռնային Ղարաբաղի Հանրապետության[Ն 1] պետական լեզուն է։ Իր շուրջ հինգհազարամյա գոյության ընթացքում հայերենը շփվել է տարբեր ժողովուրդների, բազմաթիվ լեզուների հետ, սակայն պահպանել է իր ինքնուրույնությունը, քերականական կառուցվածքի և բառապաշարի (բառային ֆոնդի) ինքնատիպությունը։\n\n" +
                "Հայոց լեզվով ստեղծվել է մեծ գրականություն։ Գրաբարով է ավանդված հայ հին պատմագրությունը, գիտափիլիսոփայական, մաթեմատիկական, բժշկագիտական, աստվածաբանական-դավանաբանական գրականությունը։ Միջին գրական հայերենով են մեզ հասել միջնադարյան հայ քնարերգության գլուխգործոցները, բժշկագիտական, իրավագիտական նշանակալի աշխատություններ։ Գրական նոր հայերենի արևելահայերեն ու արևմտահայերեն գրական տարբերակներով ստեղծվել է գեղարվեստական, հրապարակախոսական ու գիտական բազմատիպ ու բազմաբնույթ հարուստ գրականություն։\n\n" +
                "Հայերենը լայնորեն օգտագործվում է պատմական Հայաստանի տարածքներում (Ջավախք, Պարսկահայք, Արևմտյան Հայաստանի որոշ շրջաններ) և Հայկական սփյուռքում։ Առավել կիրառական է Եվրոպայում (Ֆրանսիա, Գերմանիա, Իսպանիա, Բելգիա, Շվեյցարիա, Իտալիա, Հունաստան, Բուլղարիա և այլն), Մերձավոր Արևելքում (հիմնականում Իրան, Սիրիա, Լիբանան, Իրաք, Պաղեստին, Իսրայել, Եգիպտոս, մասամբ՝ Թուրքիա) և նախկին ԽՍՀՄ հանրապետություններում՝ Ռուսաստանի Դաշնությունում (Հարավային դաշնային տարածաշրջան, Մոսկվա և խոշոր քաղաքներ), Վրաստանում, Ուկրաինայում և այլուր։\n\n" +
                "Արևմտյան կիսագնդում հայերեն են խոսում ԱՄՆ-ում, Կանադայում, Լատինական Ամերիկայում (Ուրուգվայ, Արգենտինա, Բրազիլիա)։ Հայկական համայնքներ կան նաև Աֆրիկայում, Ավստրալիայում և այլուր։";
        String dummyTitle = "Trip target Maymekh";
        Address dummyAddress = new Address("Mt Maymekh");
        return new PlaceDetail(R.drawable.trip1, dummyTitle, dummyDescription, dummyAddress, "place_1");
    }


    public static TripDetail dummyTripDetail() {
        String dummyDescription = "Հայերեն (ավանդական՝ հայերէն), հնդեվրոպական լեզվաընտանիքի առանձին ճյուղ հանդիսացող լեզու։ Հայաստանի և Լեռնային Ղարաբաղի Հանրապետության[Ն 1] պետական լեզուն է։ Իր շուրջ հինգհազարամյա գոյության ընթացքում հայերենը շփվել է տարբեր ժողովուրդների, բազմաթիվ լեզուների հետ, սակայն պահպանել է իր ինքնուրույնությունը, քերականական կառուցվածքի և բառապաշարի (բառային ֆոնդի) ինքնատիպությունը։\n\n" +
                "Հայոց լեզվով ստեղծվել է մեծ գրականություն։ Գրաբարով է ավանդված հայ հին պատմագրությունը, գիտափիլիսոփայական, մաթեմատիկական, բժշկագիտական, աստվածաբանական-դավանաբանական գրականությունը։ Միջին գրական հայերենով են մեզ հասել միջնադարյան հայ քնարերգության գլուխգործոցները, բժշկագիտական, իրավագիտական նշանակալի աշխատություններ։ Գրական նոր հայերենի արևելահայերեն ու արևմտահայերեն գրական տարբերակներով ստեղծվել է գեղարվեստական, հրապարակախոսական ու գիտական բազմատիպ ու բազմաբնույթ հարուստ գրականություն։\n\n" +
                "Հայերենը լայնորեն օգտագործվում է պատմական Հայաստանի տարածքներում (Ջավախք, Պարսկահայք, Արևմտյան Հայաստանի որոշ շրջաններ) և Հայկական սփյուռքում։ Առավել կիրառական է Եվրոպայում (Ֆրանսիա, Գերմանիա, Իսպանիա, Բելգիա, Շվեյցարիա, Իտալիա, Հունաստան, Բուլղարիա և այլն), Մերձավոր Արևելքում (հիմնականում Իրան, Սիրիա, Լիբանան, Իրաք, Պաղեստին, Իսրայել, Եգիպտոս, մասամբ՝ Թուրքիա) և նախկին ԽՍՀՄ հանրապետություններում՝ Ռուսաստանի Դաշնությունում (Հարավային դաշնային տարածաշրջան, Մոսկվա և խոշոր քաղաքներ), Վրաստանում, Ուկրաինայում և այլուր։\n\n" +
                "Արևմտյան կիսագնդում հայերեն են խոսում ԱՄՆ-ում, Կանադայում, Լատինական Ամերիկայում (Ուրուգվայ, Արգենտինա, Բրազիլիա)։ Հայկական համայնքներ կան նաև Աֆրիկայում, Ավստրալիայում և այլուր։";
        String dummyTitle = "Trip target Maymekh";
        String dummyStartDate = "Monday, September 13 - 08:00";
        Address dummyFromAddress = new Address("Tigran Mets 15/34");
        Address dummyTarget = new Address("Mt Maymekh");
        return new TripDetail(R.drawable.trip1, dummyTitle, dummyDescription, dummyStartDate, dummyFromAddress, dummyTarget, "trip_1");
    }
}
