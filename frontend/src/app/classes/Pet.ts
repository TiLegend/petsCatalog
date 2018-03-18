import { IPet } from "../interface/IPet";
import { PetType } from "./PetType";

export class Pet implements IPet {
    id: number;
    nickname: string;
    birthDay: Date;
    sex: string;
    petType: PetType;
}