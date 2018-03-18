import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { PetService } from '../pet.service';

@Component({
  selector: 'app-add-pet',
  templateUrl: './add-pet.component.html',
  styleUrls: ['./add-pet.component.scss']
})
export class AddPetComponent implements OnInit {

  title = 'Add Pet';
  petForm: FormGroup;

  constructor(private petService: PetService,
    private fb: FormBuilder) { }

  ngOnInit() {
  }

}
