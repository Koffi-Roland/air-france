export enum COMMON_DATA_TYPE {
    STRING = "String",
    DATE = "Date",
    BOOLEAN = "Boolean"
}

/**
 * Use the same namespace as the enum, in order to append functions to the enum
 */
export namespace COMMON_DATA_TYPE {

    /**
     * detect if the type exists in the common dataTypes
     * 
     * @param type string
     * @returns boolean
     */
    export function isCommonType(type: string): boolean {
        return Object.values(COMMON_DATA_TYPE).some(e => e === type);
    }

}