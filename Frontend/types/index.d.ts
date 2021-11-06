export interface ITalk {
  id: string;
  date: string;
  message: string | null;
  owner: string;
  own: boolean;
}

export interface IMember {
  id: number;
  userId: number;
  name: string;
}

export interface IUser {
  id: number;
  userId: string;
  name: string;
  email: string;
}

export interface IRoom {
  id: string;
  latestDate: string;
  latestMessage: string;
  members: IMember[];
}
