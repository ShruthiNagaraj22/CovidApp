import { Component, OnInit } from '@angular/core';
import { City, CityInfections, CityInfo, HttpClientService } from '../service/http-client.service';
import { ChartData, ChartOptions } from 'chart.js';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cities',
  templateUrl: './cities.component.html',
  styleUrls: ['./cities.component.css']
})
export class CitiesComponent implements OnInit {

  cities: City[] = [];
  cityInfections : CityInfections[] = [];
  cityInfo : CityInfo = new CityInfo("", this.cityInfections);
  selectedCityId: number = 0;
  fromDate: string = "";
  toDate: string = "";
  date: Date = new Date();
  isTableVisible: boolean = true;
  isChartVisible: boolean = true;
  isExportVisible: boolean = true;
  page: number = 1;
  count: number = 0;
  tableSize: number = 20;

  errorMessage: string = '';


  chartOptions!: ChartOptions;
  covidData!: ChartData<'line'>;

  constructor(
    private httpClientService:HttpClientService
  ) { }

  ngOnInit() {

    this.httpClientService.getCities().subscribe(
     (response) =>{this.cities = response;}
     
    );
  }

  //event to receive selected city
  selectChangeHandler (event: any) {
    this.selectedCityId = event.target.value;
  }

  //event to receive from date
  sendFromDate (event: any){
    this.fromDate = event.target.value;
  }

  //event to receive to date
  sendToDate (event: any){
    this.toDate = event.target.value;
  }

  //--------------------------------table-------------------------------------

  //methods for pagination
  onTableDataChange(event: any) {
    this.page = event;
  }
  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    
  }

  //method to get list of cities from service
  OnGetCityInfo() {

    if(this.selectedCityId){
      this.httpClientService.getCityInfo(
        this.selectedCityId, this.fromDate.toString(), this.toDate.toString()
      ).subscribe({
        next: (response) => {
          
          this.cityInfections = response;

          this.displayChart();

          if(Object.keys(this.cityInfections).length){
            this.isTableVisible = false; 
            this.isChartVisible = false;
            this.isExportVisible = false;
          }else{
            Swal.fire('No data to display for the city');
          }
      
        },
        error: (error) => {
          console.error('error caught in component')
          console.error(error)
          this.errorMessage = error;
        },
        complete: () => console.info('complete')
      });

    }else{
      Swal.fire({icon: 'warning',text:'Please select a City'});
    }

  }
  //--------------------------------table-------------------------------------

  //--------------------------------charts------------------------------------
  displayChart(){
      
      let data = this.cityInfections;
      length = data.length;

      let labels = [];
      let totalCasevalues = [];
      let activeCasevalues = [];
            
      for (let i = 0; i < length; i++) {
              labels.push(data[i].date);
              totalCasevalues.push(data[i].totalCases);
              activeCasevalues.push(data[i].activeCases);
      }
       
      this.chartOptions = {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: 'Covid Infections Report',
          },
        },
      };

      this.covidData = {
        labels: labels,
        datasets: [
          { label: 'Total Cases', data: totalCasevalues, tension: 0.5 },
          { label: 'Active Cases', data: activeCasevalues, tension: 0.5 },
        ],
      };

  }
  //--------------------------------charts-------------------------------------


  //----------------------------Export CSV-------------------------------------

  //method to get the csv data from service
    
  exportCSV(){

    if(this.selectedCityId){
      
      this.httpClientService.getCSV(this.selectedCityId, this.fromDate.toString(), this.toDate.toString()).subscribe(
        (response) => {
          this.downloadCSV(response);
        });
      }else{
        Swal.fire({icon: 'warning',text:'Please select a City'});
      }

  }

  downloadCSV(blob : Blob) {

    // Creating an object for downloading url
    const url = window.URL.createObjectURL(blob)

    // Creating an anchor(a) tag of HTML
    const a = document.createElement('a')

    // Passing the blob downloading url
    a.setAttribute('href', url)

    // Setting the anchor tag attribute for downloading
    // and passing the download file name
    a.setAttribute('download', 'data.csv');

    // Performing a download with click
    a.click()
  }
  //----------------------------Export CSV-------------------------------------

}


   
  
