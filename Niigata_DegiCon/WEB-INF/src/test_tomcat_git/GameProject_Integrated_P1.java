package test_tomcat_git;

public class GameProject_Integrated_P1 extends GameProject_Main
{
	void IntegratedP1(int[] playerinfo)
	{
		System.out.println("ｐ１で入った時の統合処理");

		//自分のテキストの情報をもってくる
		for (int i = 0; i < textmain.length; i++)
		{
			//テキストの０行目から順番に持ってきて退避
			textW = txR.read(playerinfo[1], playerinfo[2], i);
			for (int j = 0; j < textmain[0].length; j++)
			{
				w = textW[j];//退避させた１次元配列をさらに１つずつ退避
				textmain[i][j] = w;//textmainに格納
			}
		}

		textF = txR.read(playerinfo[1], 2, 3);//ｐ２が使ったカードの情報を持ってきて退避

		System.out.println("ｐ１の統合でｐ２の使ったカードを持ってくる" + textF);

		//ｐ１のところにｐ２の情報を持ってくる
		for (int i = 0; i < p2_card.length; i++)
		{
			textmain[5][i] = textF[i];//２次元配列のｐ２のカード情報のところにセット

			p2_card[i][0] = textF[i];//ｐ２のカード情報の場所にもセット
			p1_card[i][0] = textmain[3][i];//２次元配列からｐ１の使ったカードの情報をセット
		}

		//ｐ１の使ったカードの情報をDBから持ってくる
		for (int i = 0; i < p1_card.length; i++)
		{
			//使ったカードがあったら（つまり、－１でなければ）
			if (p1_card[i][0] != -1)
			{
				p1_cardinfo = DBC.reference(p1_card[i][0]);//p1の使ったカード情報をDBからもってくる

				for (int j = 0; j < 5; j++)
				{
					//ｐ１の情報を入れていく
					w = p1_cardinfo[j];
					p1_card[i][j] = w;
				}
			}
			System.out.println("p1の使用したカード：" + p1_card[i][0]);
		}

		//ｐ２の使ったカードの情報をDBから持ってくる
		for (int i = 0; i < p2_card.length; i++)
		{
			//使ったカードがあったら（つまり、－１でなければ）
			if (p2_card[i][0] != -1)
			{
				p2_cardinfo = DBC.reference(p2_card[i][0]);//p2の使ったカード情報をDBからもってくる

				for (int j = 0; j < 5; j++)
				{
					//ｐ２の情報を入れていく
					w = p2_cardinfo[j];
					p2_card[i][j] = w;
				}
			}
			System.out.println("p2の使用したカード：" + p1_card[i][0]);
		}

		//統合処理:計算
		for (int i = 0; i < p1_card.length; i++)
		{
			//ｐ１が選んだお酒にｐ２が選んだおつまみがマッチしてるかどうか
			if (i == 0)
			{
				for (int j = 3; j < p2_card[0].length; j++)
				{
					//マッチした場合はフラグをtrue
					if (p1_card[i][0] == p2_card[1][j])
					{
						flag = true;
					}

					//マッチしなかった場合は何もしない
					else
					{
					}
				}

				if (flag == true)
				{
					textmain[4][0] = (int) (p1_card[0][2] * 1.5);
					textmain[6][1] = textmain[4][0];
					flag = false;//フラグを元に戻す
				}
				else
				{
					textmain[4][0] = p1_card[0][2];
					textmain[6][1] = 0;
					flag = false;//フラグを元に戻す
				}
			}

			//ｐ１が選んだおつまみがｐ２のお酒にマッチしているかどうか
			else if (i == 1)
			{
				for (int j = 3; j < p2_card[1].length; j++)
				{
					//マッチした場合はフラグをtrue
					if (p1_card[i][0] == p2_card[0][j])
					{
						flag = true;
					}

					//マッチしなかった場合は何もしない
					else
					{
					}
				}

				if (flag == true)
				{
					textmain[4][1] = (int) (p2_card[0][2] * 1.5);
					textmain[6][0] = textmain[4][1];
					flag = false;//フラグを元に戻す
				}
				else
				{
					textmain[4][1] = 0;
					textmain[6][0] = p2_card[0][2];
					flag = false;//フラグを元に戻す
				}
			}

			//３番目はー１で何も入っていない
			else
			{

				textmain[4][2] = 0;
				textmain[6][2] = 0;
			}
		}

		//統合処理の結果、発生した満足度を増やす
		//ｐ１の満足度を増やす
		textmain[1][1] += textmain[4][0];
		//満足度が１００を超えたら、１００にする
		if (textmain[1][1] >= 100)
		{
			textmain[1][1] = 100;
		}

		//ｐ２の満足度を増やす
		textmain[1][2] += textmain[6][0];
		//満足度が１００を超えたら、１００にする
		if (textmain[1][2] >= 100)
		{
			textmain[1][2] = 100;
		}

		textmain[2][1]++;//ｐ１の行動値を１増やす
		textmain[2][2]++;//ｐ２の行動値を２増やす

		System.out.println("ここからプレイヤー１のテキスト書き込み");

		//ｐ１の統合処理後の情報をテキストに書き込む
		for (int i = 0; i < textmain.length; i++)
		{
			for (int j = 0; j < textmain[1].length; j++)
			{
				w = textmain[i][j];//２次元配列の情報をセット
				textW[j] = w;
			}
			System.out.println("書き込み7の" + i + "回目");
			txW.write(playerinfo[1], playerinfo[2], i, textW);//テキストに書き込み
		}

		//統合処理後に各プレイヤーの処理判定を０に戻す
		/*ここでtextmainの処理判定部分を０に戻せばこの後に行われるｐ２の情報を
		 	書き込む時にも０を入れてくれる*/
		for (int i = 0; i < textW.length; i++)
		{
			if (i == 0)
			{
				textmain[0][0] = 0;
				w = textmain[0][0];
				textW[i] = w;
			}

			else
			{
				textW[i] = -1;
			}
		}
		System.out.println("書き込み8");
		txW.write(playerinfo[1], 1, 0, textW);//ｐ１の処理判定のところを書き換える

		System.out.println("ここからｐ１で入った時のプレイヤー２のテキスト書き込み");

		//ｐ２の統合処理後の情報をテキストに書き込む
		for (int i = 0; i < textmain.length; i++)
		{
			switch (i)
			{
				case 3:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[5][j];
						textW[j] = w;
					}
					break;
				case 4:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[6][j];
						textW[j] = w;
					}
					break;
				case 5:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[3][j];
						textW[j] = w;
					}
					break;
				case 6:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[4][j];
						textW[j] = w;
					}
					break;
				default:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[i][j];//２次元配列の情報をセット
						textW[j] = w;
					}
			}
			System.out.println("書き込み9の" + i + "回目");
			txW.write(playerinfo[1], 2, i, textW);//テキストに書き込み
		}

		System.out.print("ｐ１が１枚目のカードで与えるダメージ：" + textmain[4][0]);
		System.out.print("ｐ１が２枚目のカード与えるダメージ：" + textmain[4][1]);
		System.out.println("ｐ１が３枚目のカードで与えるダメージ：" + textmain[4][2]);
		System.out.print("ｐ２が１枚目のカードで与えるダメージ：" + textmain[6][0]);
		System.out.print("ｐ２が２枚目のカードで与えるダメージ：" + textmain[6][1]);
		System.out.println("ｐ２が３枚目のカードで与えるダメージ：" + textmain[6][2]);

	}

	//クールタイム処理
	void setCT(int[] playerinfo)
	{

		//クールタイムテキストから情報を持ってくる
		for (int i = 0; i < CT.length; i++)
		{
			//各プレイヤーのクールタイムテキストから情報を持ってきて退避
			textF = txR.read(playerinfo[1], 4, i);
			for (int j = 0; j < CT[0].length; j++)
			{
				w = textF[j];//wに退避
				CT[i][j] = w;//対応した場所に格納
			}
		}

		//クールタイムの短縮処理
		for (int i = 0; i < CT.length; i++)
		{
			for (int j = 0; j < CT[0].length; j++)
			{
				if (CT[i][j] > 0)
				{
					CT[i][j]--;
				}
			}
		}

		//使ったカードのクールタイムをセット
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < textmain[3].length; j++)
			{
				//ｐ１のCTの設定
				if (i == 0)
				{
					if (textmain[3][j] != -1)
					{
						//使用したカードIDのところにクールタイムをセット
						CT[i][textmain[3][j]] = p1_card[j][1];
					}
				}
				//ｐ２のCTの設定
				else if (i == 1)
				{
					if (textmain[5][j] != -1)
					{
						//使用したカードIDのところにクールタイムをセット
						CT[i][textmain[5][j]] = p2_card[j][1];
					}
				}
			}
		}

		//更新したクールタイムの情報をテキストに書き込む
		for (int i = 0; i < CT.length; i++)
		{
			//各プレイヤーのクールタイムの情報を１次元配列に退避させる
			for (int j = 0; j < CT[0].length; j++)
			{
				w = CT[i][j];
				CTwrite[j] = w;
			}
			txW.write(playerinfo[1], 4, i, CTwrite);//テキストに書き込み
		}
	}
}
