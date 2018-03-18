import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Pet } from './classes/Pet';
import { PetType } from './classes/PetType';

@Injectable()
export class PetService {

  constructor(
    private httpClient: HttpClient
  ) { }

  private userPetsApi = 'http://localhost:8181/api/pets';
  private updateUserPetApi = 'http://localhost:8181/api/pet';
  private petFindByIdApi = 'http://localhost:8181/api/pet/';
  private deleteUserPetApi = 'http://localhost:8181/api/pet/delete/';
  private petTypesApi = 'http://localhost:8181/api/pettypes';
  private gendersApi = 'http://localhost:8181/api/genders';

  getUserPets(): Observable<Pet[]> {
    return this.httpClient.get<Pet[]>(this.userPetsApi);
  }

  updateUserPet(_pet): Observable<Pet> {
    return this.httpClient.post<Pet>(this.updateUserPetApi,_pet);
  }

  deleteUserPet(id): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(this.deleteUserPetApi + id);
  }

  petTypes(): Observable<PetType[]> {
    return this.httpClient.get<PetType[]>(this.petTypesApi);
  }

  genders(): Observable<string[]> {
    return this.httpClient.get<string[]>(this.gendersApi);
  }

  findPetById(id): Observable<Pet> {
    return this.httpClient.get<Pet>(this.petFindByIdApi + id);
  }

}
