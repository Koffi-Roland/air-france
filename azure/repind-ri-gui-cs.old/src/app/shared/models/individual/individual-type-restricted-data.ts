export namespace IndividualTypeRestrictedData {

    /**
     * Ressources data of individual's types who has restriction on update
     */
    export enum restrictedTypes {
        TRAVELER = 'T',
        WHITE_WINGER = 'W', // Prospects
        CALLER = 'C',
    }


    export function haveRestriction(type: string): boolean {
        const restricted = (element) => type === element;
        return Object.values(restrictedTypes).some(restricted);
    }

    export function restrictAnyUpdates(type: string): boolean {
        return type === restrictedTypes.TRAVELER
    }


    /**
     * Restrict all individual types listed in the enum,
     * Allow only types listed here
     * @param type
     */
    export function restrictComPrefUpdates(type: string): boolean {
        return type === (restrictedTypes.CALLER || restrictedTypes.WHITE_WINGER);
    }
}
