package com.peauty.domain.puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Breed {

    // 소형견
    AFFENPINSCHER("아펜핀셔", PuppySize.SMALL),
    TERRIER("테리어", PuppySize.SMALL),
    BEAGLE("비글", PuppySize.SMALL),
    BICHON_FRISE("비숑", PuppySize.SMALL),
    CHIHUAHUA("치와와", PuppySize.SMALL),
    DACHSHUND("닥스훈트", PuppySize.SMALL),
    MALTESE("말티즈", PuppySize.SMALL),
    POMERANIAN("포메라니안", PuppySize.SMALL),
    PUG("퍼그", PuppySize.SMALL),
    SHIHTZU("시츄", PuppySize.SMALL),

    // 중형견
    SHEPHERD("셰퍼드", PuppySize.MEDIUM),
    BORDER_COLLIE("보더콜리", PuppySize.MEDIUM),
    BULLDOG("불독", PuppySize.MEDIUM),
    DALMATIAN("달마시안", PuppySize.MEDIUM),
    POODLE("푸들", PuppySize.MEDIUM),

    // 대형견
    RETRIEVER("리트리버", PuppySize.LARGE),
    SAINT_BERNARD("세인트 버나드", PuppySize.LARGE),
    SAMOYED("사모예드", PuppySize.LARGE);

    private final String breedName;
    private final PuppySize puppySize;

    Breed(String breedName, PuppySize puppySize) {
        this.breedName = breedName;
        this.puppySize = puppySize;
    }

    public static Breed from(String breedName) {
        return Arrays.stream(Breed.values())
                .filter(it -> it.breedName.equalsIgnoreCase(breedName))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXISTS_BREED));
    }
}


/*
    Affenpinscher,
    Afghan_Hound,
    Airedale_Terrier,
    Akita,
    Alaskan_Malamute,
    American_Staffordshire_Terrier,
    American_Water_Spaniel,
    Australian_Cattle_Dog,
    Australian_Shepherd,
    Australian_Terrier,
    Basenji,
    Basset_Hound,
    Beagle,
    Bearded_Collie,
    Bedlington_Terrier,
    Bernese_Mountain_Dog,
    Bichon_Frise,
    Black_And_Tan_Coonhound,
    Blood_Hound,
    Border_Collie,
    Border_Terrier,
    Borzoi,
    Boston_Terrier,
    Bouvier_Des_Flandres,
    Boxer,
    Briard,
    Brittany,
    Brussels_Griffon,
    BullTerrier,
    Bulldog,
    Bullmastiff,
    Cairn_Terrier,
    Canaan_Dog,
    Chesapeake_Bay_Retriever,
    Chihuahua,
    Chinese_Crested,
    Chinese_Shar_Pei,
    Chow_Chow,
    Clumber_Spaniel,
    Cocker_Spaniel,
    Collie,
    Curly_Coated_Retriever,
    Dachshund,
    Dalmatian,
    Doberman_Pinscher,
    English_Cocker_Spaniel,
    English_Setter,
    English_Springer_Spaniel,
    English_Toy_Spaniel,
    Eskimo_Dog,
    Finnish_Spitz,
    Flat_Coated_Retriever,
    Fox_Terrier,
    Fox_hound,
    French_Bulldog,
    German_Shepherd,
    German_Shorthaired_Pointer,
    German_Wirehaired_Pointer,
    Golden_Retriever,
    Gordon_Setter,
    Great_Dane,
    Greyhound,
    Irish_Setter,
    Irish_Water_Spaniel,
    Irish_Wolfhound,
    Jack_Russell_Terrier,
    Japanese_Spaniel,
    Keeshond,
    Kerry_Blue_Terrier,
    Komondor,
    Kuvasz,
    Labrador_Retriever,
    Lakeland_Terrier,
    Lhasa_Apso,
    Maltese,
    Manchester_Terrier,
    Mastiff,
    Mexican_Hairless,
    Newfoundland,
    Norwegian_Elkhound,
    Norwich_Terrier,
    Otterhound,
    Papillon,
    Pekingese,
    Pointer,
    Pomeranian,
    Poodle,
    Pug,
    Puli,
    Rhodesian_Ridgeback,
    Rottweiler,
    Saint_Bernard,
    Saluki,
    Samoyed,
    Schipperke,
    Schnauzer,
    Scottish_Deerhound,
    Scottish_Terrier,
    Sealyham_Terrier,
    Shetland_Sheepdog,
    ShihTzu,
    Siberian_Husky,
    Silky_Terrier,
    Skye_Terrier,
    Staffordshire_BullTerrier,
    Soft_Coated_Wheaten_Terrier,
    Sussex_Spaniel,
    Spitz,
    Tibetan_Terrier,
    Vizsla,
    Weimaraner,
    Welsh_Terrier,
    West_Highland_White_Terrier,
    Whippet,
    Yorkshire_Terrier
    */
