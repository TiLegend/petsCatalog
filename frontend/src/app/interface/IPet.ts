import { PetType } from "../classes/PetType";

export interface IPet {
    id: number;
    nickname: string;
    birthDay: Date;
    sex: string;
    petType: PetType;
}