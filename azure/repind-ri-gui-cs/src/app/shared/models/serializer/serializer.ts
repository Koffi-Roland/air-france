import { Resource } from '../resources/resource';



export abstract class Serializer {
  /**
   * This is the method to deserialize the JSON given by the backend.
   * The idea is to parse the JSON and build a front-end model (a Resource) that
   * can be used in the UI later on.
   *
   * @param json - the JSON response from the backend
   */
  abstract fromJsonToObject(json: any): any;



  /**
   * This is the method to send front-end models to backend.
   * The idea is to build a JSON format payload that will then be sent to the server.
   *
   * @param resource - the resource that has to be sent to backend
   */
  abstract toJson(resource: Resource): any;


  /**
   * This method, calling by the *`ResourceService`*, just after recieving the data
   * from the API.
   * it can be used to apply custom treatment before using in the application, by
   * overwrite the method into a child-class.
   * @param json - the data freshly recieved from API
   */

  fromJsonToArrayObject(json: any): any[] {
    return json.map((item: any) => this.fromJsonToObject(item));
  }


}
