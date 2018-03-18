import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Http } from '@angular/http';
import { PetService } from '../pet.service';
import { Pet } from '../classes/Pet';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CustomValidators } from '../validators/CustomValidators';
import {SelectButtonModule} from 'primeng/selectbutton';
import { PetType } from '../classes/PetType';
import { DataTableModule, SharedModule, ButtonModule, DialogModule } from 'primeng/primeng';//PrimeNg
import { AuthService } from '../auth.service';
import { TokenParams } from '../classes/TokenParams';

@Component({
  selector: 'app-pets',
  templateUrl: './pets.component.html',
  styleUrls: ['./pets.component.scss']
})
export class PetsComponent implements OnInit {

  pets: Pet[];
  genders: string[];
  petTypes: PetType[];
  public pet = new Pet();
  public isAdd: Boolean = false;
  public isEdit: Boolean = false;
  public pets_error: Boolean = false;
  public isLoadingData: Boolean = false;
  findPetId: number;

  addPetFG: FormGroup;
  editPetFG: FormGroup;

  addSuccess: boolean;
  editSuccess: boolean;

  displayAddDialog: boolean = false;
  displayEditDialog: boolean = false;
  displayPetFindDialog: boolean = false;

  constructor(
    private router: Router,
    public petService: PetService,
    private fb: FormBuilder,
    private authService:AuthService
  ) { }

  ngOnInit() {
    
    this.addPetFG = this.fb.group({
      'nickname': [null, [Validators.required]],
      'birthDay': [null, [Validators.required]],
      'sex': [null, Validators.required],
      'petType': [null, Validators.required]
    });

    this.editPetFG = this.fb.group({
      'nickname': [null, [Validators.required]],
      'birthDay': [null, [Validators.required]],
      'sex': [null, Validators.required],
      'petType': [null, Validators.required]
    });

    this.getAllPets();
    this.getGenders();
    this.getPetTypes();


    this.isAdd = true;
    this.isEdit = false;
  }

  editPet(_pet:Pet){
    this.displayEditDialog = true;
    this.isEdit = true;
    this.isAdd = false;
    let currentbirthDay = new Date(_pet.birthDay);
    this.pet = {id:_pet.id, nickname:_pet.nickname, birthDay:currentbirthDay, sex:_pet.sex, petType:_pet.petType}
  }

  updatePet(pet: Pet){
    this.petService.updateUserPet(pet).subscribe(
      data => {
        this.getAllPets();
        alert('Pet Updated Successfully!');
        this.editSuccess = true;
        this.displayEditDialog = false;
        this.pet = new Pet();
        this.editPetFG.reset();
        this.isEdit = false;
        this.isAdd = true;
        return true;
    },
    error => {
        console.error("Error saving Pet!");
        this.editSuccess = false;
        alert(error.message);
    }
    ); 
  }

  addPet(pet: Pet) {

    this.isAdd = true;
    this.isEdit = false;
    this.displayEditDialog = false;
  
    this.petService.updateUserPet(pet).subscribe(
        data => {
            this.getAllPets();
            alert('pet Added Successfully!');
            this.addSuccess = true;
            this.displayAddDialog = false;
            this.pet = new Pet();
            this.addPetFG.reset();
            return true;
        },
        error => {
            console.error("Error saving Pet!");
            this.addSuccess = false;
            alert(error);
        }
    );
  }

  deletePet(pet){
    return this.petService.deleteUserPet(pet.id).subscribe(
      data => {
          alert('Pet Deleted Successfully!');
          this.getAllPets();
          return true;
      },
      error => {
          this.isLoadingData = false;
          console.error("Error deleting Pet!");
          alert(error);
      },
      () => {
          this.isLoadingData = false;
      }
  );
  }

  clearData(): void {
    this.pet = new Pet();
    this.isEdit = false;
    this.isAdd = true;

    this.displayAddDialog = false;
    this.displayEditDialog = false;
    this.displayPetFindDialog = false;
}

addPetDialog() {
  this.displayEditDialog = false;
        this.pet = new Pet();
        this.isEdit = false;
        this.isAdd = true;
  this.displayAddDialog = true;
}

  getAllPets() {
    this.petService.getUserPets().subscribe(res => {
      this.pets = res;
    },
      error => {
        console.log(error),
          this.isLoadingData = false;
      },
      () => {
        this.isLoadingData = false;
      });
  }

  getGenders(){
    this.petService.genders().subscribe(res => {

      this.genders = res;
    });
  }

  getPetTypes(){
    this.petService.petTypes().subscribe(res => {
      this.petTypes = res;
    });
  }

  findPet(){
    let aaa = this.authService.getNewAcessToken();
    this.isAdd = false;
    this.isEdit = false;
    this.petService.findPetById(this.findPetId).subscribe(res => {
      if(res != null){
        this.displayPetFindDialog = true;
        this.pet = res;
      } else alert("Pet not exist")
    },
    error => {
        this.isLoadingData = false;
        console.error("Error searching pet!");
        alert(error.message);
    })
  }


}
