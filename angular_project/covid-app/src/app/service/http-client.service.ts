import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { retry, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';


export class City{
  constructor(
    public cityId:number,
    public cityName:string,
    public population:number
  ) {
      this.cityId = cityId;
      this.cityName = cityName;
      this.population = population;
  }
}

export class CityInfo{

  constructor(
    public cityName:string,
    public cityInfections: CityInfections[]
  ) {
      this.cityInfections = cityInfections;
      this.cityName = cityName;
  }

  
}

export class CityInfections{
  constructor(
    public date:string,
    public totalCases:number,
    public activeCases:number
  ) {
      this.date = date;
      this.totalCases = totalCases;
      this.activeCases = activeCases;
  }
}


@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(
    private httpClient:HttpClient
  ) { 
     }

  // method to get list of cities
  getCities()
  {
    return this.httpClient.get<City[]>('http://localhost:8080/covidApp/getCities');
  }
  
  // method to get city information on covid cases
  getCityInfo(cityId : number, from : string, until : string) 
  {
    
    const params = new HttpParams()
    .set('cityId', cityId)
    .set('from', from)
    .set('until', until);

    return this.httpClient.get<CityInfections[]>('http://localhost:8080/covidApp/city_infections', {params}).pipe(retry(1), catchError(this.handleError));
    

  }

  // method to get csv data from backend
  getCSV(cityId : number, from : string, until : string) // to get list of cities
  {
    const params = new HttpParams()
    .set('cityId', cityId)
    .set('from', from)
    .set('until', until);

    return this.httpClient.get('http://localhost:8080/covidApp/export_csv', {params:params, responseType:'blob'} 
    ).pipe(retry(1), catchError(this.handleError));
  }

  
  handleError(error:any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(() => {
        return errorMessage;
    });
  }

}