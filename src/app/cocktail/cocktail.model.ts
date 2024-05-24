export interface Cocktail {
    id: number;
    name: string;
}

export interface Page {
    content: Cocktail[];
    first: boolean;
    last: boolean;
    number: number;
}
