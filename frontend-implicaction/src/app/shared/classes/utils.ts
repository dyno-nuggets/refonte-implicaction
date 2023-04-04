export class Utils {

  public static isEqual = (o1: any, o2: any): boolean => JSON.stringify(o1) === JSON.stringify(o2);

  public static sortDateByYearDesc = (a: string, b: string): number => a ? new Date(b).getFullYear() - new Date(a).getFullYear() : -1;

}
