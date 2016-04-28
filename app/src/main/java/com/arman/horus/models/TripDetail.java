package com.arman.horus.models;

import com.arman.horus.R;

/**
 * Created by arman on 4/2/16.
 */
public class TripDetail {

    public int images;
    public String title;
    public String description;
    public String startDate;
    public Address from;
    public Address target;
    public String id;

    public TripDetail(int images, String title, String description, String startDate, Address from, Address to, String id) {
        this.images = images;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.from = from;
        this.target = to;
        this.id = id;
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
