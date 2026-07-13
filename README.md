# poja-starter-template

## Upload d'image — `POST /images`

### ⚠️ Comportement connu à respecter pour tester cet endpoint

Le paramètre `email` doit être envoyé en **query parameter**, et non comme un champ
`form-data` classique dans le corps multipart. Le paramètre `file` reste, lui, dans
le corps multipart.

**Pourquoi :** sur l'environnement de déploiement (AWS Lambda via
`aws-serverless-java-container`), le parsing du corps `multipart/form-data`
ne restitue pas correctement les champs texte (`email`) lorsqu'ils sont envoyés
aux côtés d'un fichier binaire dans le même formulaire multipart. Cela provoque
une erreur `400 Bad Request` (`MissingServletRequestParameterException`), alors
que la requête envoyée est pourtant valide.

En passant `email` par la query string, on contourne ce problème car les
paramètres d'URL sont traités par une voie indépendante du parsing multipart.

### Exemple de requête valide

```bash
curl -X POST "https://<URL_LAMBDA>/images?email=test@exemple.com" \
  -F "file=@chemin/vers/image.png;type=image/png"
```

### Avec Postman

- Onglet **Params** : ajouter `email` avec sa valeur
- Onglet **Body** → `form-data` : garder uniquement le champ `file` (type File)

### Types de fichiers acceptés

`image/jpeg`, `image/png` uniquement (voir `ImageController.ALLOWED_TYPES`).
